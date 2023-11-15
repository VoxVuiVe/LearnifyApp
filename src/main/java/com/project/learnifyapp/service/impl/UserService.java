package com.project.learnifyapp.service.impl;

import com.project.learnifyapp.dtos.UserDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.models.Role;
import com.project.learnifyapp.models.User;
import com.project.learnifyapp.repository.RoleRepository;
import com.project.learnifyapp.repository.UserRepository;
import com.project.learnifyapp.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User createUser(UserDTO userDTO) throws DataNotFoundException {
        String email = userDTO.getEmail();
        //Kiểm tra số điện thoại đã tồn tại hay chưa
        if(userRepository.existsByEmail(email)) {
            throw new DataIntegrityViolationException("Email đã tồn tại");
        }
        //Convert UserDTO -> User
        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .email(userDTO.getEmail())
                .imageUrl(userDTO.getImageUrl())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .build();
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role không được tìm thấy!"));
        newUser.setRole(role); //Tim dc ra role trong csdl se add cho newUser
        //Kiểm tra nếu có accountId, không yêu cầu password.
        //Nếu không đăng nhập bằng FB và GG sẽ yêu cầu mật khẩu
        if(userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0) {
            String password = userDTO.getPassword();
            String encodePassword = passwordEncoder.encode(password);
            //--Sẽ làm tiếp tục khi làm phần spring security
            newUser.setPassword(encodePassword);
        }
        return userRepository.save(newUser);
    }

    @Override
    public String login(String number, String password) { //Kieu String de tra ve token key
        //Đoạn này liên quan nhiều đến security nên sẽ làm trong phần security
        //Xong tất cả API mới phân quyền
        return null;
    }
}
