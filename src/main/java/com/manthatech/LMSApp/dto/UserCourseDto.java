package com.manthatech.LMSApp.dto;

import java.util.List;

public class UserCourseDto {
    private Long userId;
    private String fullName;
    private List<String> registeredCourses;

    public UserCourseDto(Long userId, String fullName, List<String> registeredCourses) {
        this.userId = userId;
        this.fullName = fullName;
        this.registeredCourses = registeredCourses;
    }

    public Long getUserId() {
        return userId;
    }

    public UserCourseDto setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserCourseDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public List<String> getRegisteredCourses() {
        return registeredCourses;
    }

    public UserCourseDto setRegisteredCourses(List<String> registeredCourses) {
        this.registeredCourses = registeredCourses;
        return this;
    }
}
