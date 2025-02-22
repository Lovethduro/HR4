package org.example.counters;

public class RequestCounter {
    private static int requestCount = 0;

    public static synchronized void increment() {
        requestCount++;
    }

    public static int getRequestCount() {
        return requestCount;
    }
}