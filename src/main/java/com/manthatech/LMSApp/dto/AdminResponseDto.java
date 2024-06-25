package com.manthatech.LMSApp.dto;

import com.manthatech.LMSApp.model.Course;
import com.manthatech.LMSApp.model.User;

import java.util.Set;

public class AdminResponseDto {
    private Long id;
    private String fullName;
    private String email;
    private String role;
    private Set<Course> courses;

    public AdminResponseDto(Long id, String fullName, String email, String role, Set<Course> courses) {
        this.fullName = fullName;
        this.id = id;
        this.email = email;
        this.role = role;
        this.courses = courses;
    }

    public AdminResponseDto(User user) {
        this.id = user.getId();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.courses = user.getCourses();
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
