package com.manthatech.LMSApp.controller;

import com.manthatech.LMSApp.dto.AdminResponseDto;
import com.manthatech.LMSApp.dto.RegistrationDto;
import com.manthatech.LMSApp.dto.UserDto;
import com.manthatech.LMSApp.dto.UserProjection;
import com.manthatech.LMSApp.model.Course;
import com.manthatech.LMSApp.model.User;
import com.manthatech.LMSApp.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    public List<UserProjection> getAllUsers() {
        return adminService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<AdminResponseDto> getUserById(@PathVariable Long id) {
        return adminService.getUserById(id)
                .map(u -> ResponseEntity.ok(new AdminResponseDto(u)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<Object> addUser(@Valid @RequestBody RegistrationDto user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(errors);
        }
        return ResponseEntity.ok(new AdminResponseDto(adminService.addUser(user)));
    }


    @PutMapping("/users/{id}")
    public ResponseEntity<AdminResponseDto> updateUser(@PathVariable Long id, @RequestBody RegistrationDto user) {
        return ResponseEntity.ok(new AdminResponseDto(adminService.updateUser(id, user)));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/users/{userId}/courses/{courseId}")
    public ResponseEntity<AdminResponseDto> removeCourseFromUser(@PathVariable Long userId, @PathVariable Long courseId) {
        User updatedUser = adminService.removeCourseFromUser(userId, courseId);
        return ResponseEntity.ok(new AdminResponseDto(updatedUser));
    }

    @PostMapping("/users/{id}/toggle-status")
    public ResponseEntity<AdminResponseDto> toggleUserStatus(@PathVariable Long id, @RequestParam boolean enable) {
        return ResponseEntity.ok(new AdminResponseDto(adminService.toggleUserStatus(id, enable)));
    }

    @PostMapping("/users/{id}/reset-password")
    public ResponseEntity<AdminResponseDto> resetPassword(@PathVariable Long id) {
        return ResponseEntity.ok(new AdminResponseDto(adminService.resetPassword(id)));
    }

    @GetMapping("/courses")
    public List<Course> getAllCourses() {
        return adminService.getAllCourses();
    }

    @PostMapping("/courses/{courseId}/enroll-users")
    public ResponseEntity<List<AdminResponseDto>> enrollMultipleUsersInCourse(@PathVariable Long courseId, @RequestBody List<Long> userIds) {
        List<User> updatedUsers = adminService.addUsersToCourse(courseId, userIds);
        List<AdminResponseDto> response = updatedUsers.stream()
                .map(AdminResponseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users-with-courses")
    public ResponseEntity<List<UserDto>> getAllUsersWithCourses() {
        return ResponseEntity.ok(adminService.getAllUsersWithCourses());
    }

    @PostMapping("/courses")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        return ResponseEntity.ok(adminService.createCourse(course));
    }

    @PostMapping("/users/{userId}/courses/{courseId}")
    public ResponseEntity<AdminResponseDto> enrollUserInCourse(@PathVariable Long userId, @PathVariable Long courseId) {
        return ResponseEntity.ok(new AdminResponseDto(adminService.addUserToCourse(userId, courseId)));
    }
}
