package com.manthatech.LMSApp.service;

import com.manthatech.LMSApp.ExceptionHandlers.ResourceNotFoundException;
import com.manthatech.LMSApp.dto.UserCourseDto;
import com.manthatech.LMSApp.model.User;
import com.manthatech.LMSApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }

    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public UserCourseDto getUserRegisteredCourses(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        List<String> registeredCourses = user.getCourses().stream()
                .map(course -> course.getCourseName())
                .collect(Collectors.toList());

        return new UserCourseDto(user.getId(), user.getFullName(), registeredCourses);
    }
}
