package com.project.shopapp.service;

import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws DataNotFoundException;

    String login(String number, String password); //Kieu String de tra ve token key
}
