package com.manthatech.LMSApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseName;

    @ManyToMany(mappedBy = "courses")
    @JsonIgnore
    private Set<User> users;

    public Course() {
    }

    public Course(Long id, String courseName, Set<User> users) {
        this.id = id;
        this.courseName = courseName;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public Course setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCourseName() {
        return courseName;
    }

    public Course setCourseName(String courseName) {
        this.courseName = courseName;
        return this;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Course setUsers(Set<User> users) {
        this.users = users;
        return this;
    }
}
