package org.example.counters;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@WebListener // Register this class as a listener
public class SessionCounter implements HttpSessionListener {
    private static int activeSessions = 0;
    private static Set<String> sessionIds = Collections.synchronizedSet(new HashSet<>());

    // Return the session IDs of all active sessions
    public static String[] getActiveSessionDetails() {
        return sessionIds.toArray(new String[0]); // Convert Set to Array
    }

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        activeSessions++;
        sessionIds.add(event.getSession().getId()); // Add session ID
        System.out.println("Session created: " + event.getSession().getId()); // Debug: Print session ID
        System.out.println("Active sessions: " + activeSessions); // Debug: Print active session count
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        if (activeSessions > 0) activeSessions--;
        sessionIds.remove(event.getSession().getId()); // Remove session ID
        System.out.println("Session destroyed: " + event.getSession().getId()); // Debug: Print session ID
        System.out.println("Active sessions: " + activeSessions); // Debug: Print active session count
    }

    public static int getActiveSessions() {
        return activeSessions;
    }

    public static Set<String> getSessionIds() {
        return new HashSet<>(sessionIds); // Return a copy of session IDs
    }
}