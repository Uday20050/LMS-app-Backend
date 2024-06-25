package com.manthatech.LMSApp.controller;

import com.manthatech.LMSApp.dto.UserCourseDto;
import com.manthatech.LMSApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('USER')")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}/courses")
    public ResponseEntity<UserCourseDto> getUserRegisteredCourses(@PathVariable Long userId) {
        UserCourseDto userCourseDto = userService.getUserRegisteredCourses(userId);
        return ResponseEntity.ok(userCourseDto);
    }
}
