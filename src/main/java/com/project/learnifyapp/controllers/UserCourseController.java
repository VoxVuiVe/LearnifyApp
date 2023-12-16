package com.project.learnifyapp.controllers;

import com.project.learnifyapp.dtos.UserCourseDTO;
import com.project.learnifyapp.repository.UserRepository;
import com.project.learnifyapp.service.impl.UserCourseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}")
@RequiredArgsConstructor
public class UserCourseController {

    private final Logger log = LoggerFactory.getLogger(UserCourseController.class);

    private final UserCourseService userCourseService;
    private  final UserRepository userRepository;

    @GetMapping("/user-courses/{userId}")
    public ResponseEntity<List<UserCourseDTO>> getUserCourses(@PathVariable Long userId) {
        log.debug("Request get purchased course by userId: {}", userId);
        List<UserCourseDTO> userCourses = userCourseService.findAllUserById(userId);
        return ResponseEntity.ok().body(userCourses);
    }

//    public ResponseEntity

}
