package com.manthatech.LMSApp.dto;

public class CourseDto {

    private Long id;
    private String courseName;

    public CourseDto(Long id, String courseName) {
        this.id = id;
        this.courseName = courseName;
    }

    public CourseDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
