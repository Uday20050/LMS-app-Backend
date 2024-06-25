package com.manthatech.LMSApp.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String fullName;

    private String password;
    private boolean isDefaultPassword;
    private String role;
    private boolean isEnabled;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_course", // Name of the join table
            joinColumns = @JoinColumn(name = "user_id"), // Foreign key in the join table pointing to User
            inverseJoinColumns = @JoinColumn(name = "course_id") // Foreign key in the join table pointing to Course
    )
    private Set<Course> courses;


    public User() { }

    public User(Long id, String email, String password, String role, String fullName, Set<Course> courses) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
        this.courses = courses;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public User setCourses(Set<Course> courses) {
        this.courses = courses;
        return this;
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

    public User setEmail(String email) {
        this.email = email;
        return this;
    }


    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean isDefaultPassword() {
        return isDefaultPassword;
    }

    public User setDefaultPassword(boolean defaultPassword) {
        isDefaultPassword = defaultPassword;
        return this;
    }
    public String getRole() {
        return role;
    }

    public User setRole(String role) {
        this.role = role.toUpperCase();
        return this;
    }

    public User setIsEnabled(boolean enabled) {
        isEnabled = enabled;
        return this;
    }


    public String getFullName() {
        return fullName;
    }

    public User setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + this.role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
