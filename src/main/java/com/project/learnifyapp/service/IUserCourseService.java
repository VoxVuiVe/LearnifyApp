package com.project.learnifyapp.service;

import com.project.learnifyapp.dtos.UserCourseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IUserCourseService {
    @Transactional(readOnly = true)
    List<UserCourseDTO> findAllUserByIdAnd(Long userId);
}
