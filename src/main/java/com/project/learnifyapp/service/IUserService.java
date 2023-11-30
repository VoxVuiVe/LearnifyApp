package com.project.learnifyapp.service;

import com.project.learnifyapp.dtos.UserDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.models.User;

public interface IUserService {
    UserDTO createUser(UserDTO userDTO) throws DataNotFoundException;

    String login(String number, String password) throws Exception; //Kieu String de tra ve token key
}
