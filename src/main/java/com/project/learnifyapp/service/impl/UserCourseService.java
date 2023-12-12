package com.project.learnifyapp.service.impl;

import com.project.learnifyapp.dtos.UserCourseDTO;
import com.project.learnifyapp.models.UserCourse;
import com.project.learnifyapp.repository.UserCourseRepository;
import com.project.learnifyapp.service.IUserCourseService;
import com.project.learnifyapp.service.mapper.UserCourseMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCourseService implements IUserCourseService {

    private final Logger log = LoggerFactory.getLogger(UserCourseService.class);

    private final UserCourseRepository userCourseRepository;

    private final UserCourseMapper userCourseMapper;


    @Override
    @Transactional(readOnly = true)
    public List<UserCourseDTO> findAllUserById(Long userId){
        List<UserCourse> userCourses = userCourseRepository.findByUserId(userId);
        return userCourseMapper.toDTO(userCourses);
    }
}
