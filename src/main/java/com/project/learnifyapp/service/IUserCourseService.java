package com.project.learnifyapp.service;

import com.project.learnifyapp.dtos.UserCourseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IUserCourseService {

    List<UserCourseDTO> findAllUserById(Long userId);
}
