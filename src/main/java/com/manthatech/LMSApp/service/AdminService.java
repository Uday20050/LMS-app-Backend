package com.manthatech.LMSApp.service;

import com.manthatech.LMSApp.ExceptionHandlers.ResourceNotFoundException;
import com.manthatech.LMSApp.dto.*;
import com.manthatech.LMSApp.model.Course;
import com.manthatech.LMSApp.model.User;
import com.manthatech.LMSApp.repository.CourseRepository;
import com.manthatech.LMSApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String DEFAULT_PASSWORD = "defaultPassword";

    public List<UserProjection> getAllUsers() {
        return userRepository.findAllProjectedBy();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User addUser(RegistrationDto input) {
        User user = new User()
                .setFullName(input.getFullName())
                .setEmail(input.getEmail())
                .setPassword(passwordEncoder.encode(DEFAULT_PASSWORD))
                .setDefaultPassword(true)
                .setRole(input.getRole())
                .setIsEnabled(true)
                .setCourses(courseRepository.findByCourseNameIn(input.getCourseNames()));

        return userRepository.save(user);

    }

    public User updateUser(Long id, RegistrationDto input) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        if (input.getEmail() != null) user.setEmail(input.getEmail());
        if (input.getRole() != null) user.setRole(input.getRole());
        if (input.getFullName() != null) user.setFullName(input.getFullName());
        if (input.getCourseNames() != null) user.setCourses(courseRepository.findByCourseNameIn(input.getCourseNames()));
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id " + id);
        }
        userRepository.deleteById(id);
    }

    public User toggleUserStatus(Long id, boolean enable) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        user.setIsEnabled(enable);
        return userRepository.save(user);
    }

    public User resetPassword(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
        user.setDefaultPassword(true);
        return userRepository.save(user);
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<UserDto> getAllUsersWithCourses() {
        return userRepository.findAllByRole("USER").stream()
                .map(this::convertToUserDto)
                .collect(Collectors.toList());
    }
    private UserDto convertToUserDto(User user) {
        List<String> courseNames = user.getCourses().stream()
                .map(Course::getCourseName)
                .collect(Collectors.toList());
        return new UserDto(user.getId(), user.getFullName(), user.getEmail(), user.isEnabled(),courseNames);
    }


    private CourseDto convertToCourseDto(Course course) {
        return new CourseDto(course.getId(), course.getCourseName());
    }

    public List<User> addUsersToCourse(Long courseId, List<Long> userIds) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + courseId));

        List<User> users = userRepository.findAllById(userIds);
        if (users.size() != userIds.size()) {
            throw new ResourceNotFoundException("Some users not found");
        }

        users.forEach(user -> user.getCourses().add(course));
        course.getUsers().addAll(users);

        userRepository.saveAll(users); // Save users to update the relationship
        return users;
    }

    public User removeCourseFromUser(Long userId, Long courseId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + courseId));

        if (!user.getCourses().contains(course)) {
            throw new ResourceNotFoundException("Course not found for user with id " + userId);
        }

        user.getCourses().remove(course);
        course.getUsers().remove(user);

        userRepository.save(user); // Save user to update the relationship
        return user;
    }



    public User addUserToCourse(Long userId, Long courseId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + courseId));
        user.getCourses().add(course);
        return userRepository.save(user);
    }
}
