package org.example.model;

public class Employee {
    private int empId;
    private String firstName;
    private String lastName;
    private String address;
    private String createdBy;
    private String department;
    private String position;
    private String role;
    private String qualification;

    // Constructor with all fields
    public Employee(int empId, String firstName, String lastName, String address, String createdBy,
                    String department, String position, String role, String qualification) {
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.createdBy = createdBy;
        this.department = department;
        this.position = position;
        this.role = role;
        this.qualification = qualification;
    }

    // Getters and Setters
    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    // New method to get the full name of the employee
    public String getName() {
        return firstName + " " + lastName;
    }
}
