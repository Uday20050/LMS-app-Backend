package com.manthatech.LMSApp.dto;

import java.util.List;

public class UserDto {

    private Long id;
    private String email;
    private String fullName;
    private boolean isEnabled;
    private List<String> courseNames;

    public UserDto(Long id, String fullName, String email, boolean isEnabled, List<String> courseNames) {
        this.courseNames = courseNames;
        this.fullName = fullName;
        this.email = email;
        this.id = id;
        this.isEnabled = isEnabled;
    }

    public UserDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<String> getCourseNames() {
        return courseNames;
    }

    public void setCourseNames(List<String> courseNames) {
        this.courseNames = courseNames;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
