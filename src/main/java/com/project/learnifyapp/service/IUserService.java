package com.project.learnifyapp.service;

import com.project.learnifyapp.dtos.UserImageDTO;
import com.project.learnifyapp.dtos.UserDTO;
import com.project.learnifyapp.models.User;
import com.project.learnifyapp.models.UserImage;
import com.project.learnifyapp.responses.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IUserService {
    UserDTO createUser(UserDTO userDTO) throws Exception;

    String login(String email, String password, Long roleId) throws Exception; //Kieu String de tra ve token key

    UserImage createUserImage(
            Long courseId,
            UserImageDTO userImageDTO) throws Exception;

    User getUserById(Long id) throws Exception;

    Page<UserResponse> getAllUsers(String keyword, PageRequest pageRequest);

    String storeFile(MultipartFile file) throws IOException;

    void deleteFile(String filename) throws IOException;
}
