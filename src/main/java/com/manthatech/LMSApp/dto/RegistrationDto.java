package com.manthatech.LMSApp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

public class RegistrationDto {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Role is required")
    private String role;

    private Set<String> courseNames;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<String> getCourseNames() {
        return courseNames;
    }

    public void setCourseNames(Set<String> courseName) {
        this.courseNames = courseName;
    }
}
