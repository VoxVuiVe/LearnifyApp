package com.project.learnifyapp.service.impl;

import com.project.learnifyapp.components.JwtTokenUtil;
import com.project.learnifyapp.dtos.UserDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.exceptions.PermissionDeniedException;
import com.project.learnifyapp.models.Role;
import com.project.learnifyapp.models.User;
import com.project.learnifyapp.repository.RoleRepository;
import com.project.learnifyapp.repository.UserRepository;
import com.project.learnifyapp.service.IUserService;
import com.project.learnifyapp.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtil jwtTokenUtil;

    private final AuthenticationManager authenticationManager;

    private final UserMapper userMapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) throws Exception { //REGISTER USER
        //Register user
        String email = userDTO.getEmail();
        //Kiểm tra số điện thoại đã tồn tại hay chưa
        if(userRepository.existsByEmail(email)) {
            throw new DataIntegrityViolationException("Email đã tồn tại");
        }
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role không được tìm thấy!"));
        if(role.getName().toUpperCase().equals(Role.ADMIN)) {
            throw new PermissionDeniedException("You cannot register an admin account!");
        }
        //-> Cach 1:
        //Convert UserDTO -> User
//        User newUser = User.builder()
//                .fullName(userDTO.getFullName())
//                .phoneNumber(userDTO.getPhoneNumber())
//                .password(userDTO.getPassword())
//                .address(userDTO.getAddress())
//                .email(userDTO.getEmail())
//                .imageUrl(userDTO.getImageUrl())
//                .dateOfBirth(userDTO.getDateOfBirth())
//                .facebookAccountId(userDTO.getFacebookAccountId())
//                .googleAccountId(userDTO.getGoogleAccountId())
//                .build();
        //Cach 2:
        User newUser = userMapper.toEntity(userDTO);

        newUser.setRole(role); //Tim dc ra role trong csdl se add cho newUser

        //Kiểm tra nếu có accountId, không yêu cầu password.
        //Nếu không đăng nhập bằng FB và GG sẽ yêu cầu mật khẩu
        if(userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0) {
            String password = userDTO.getPassword();
            String encodePassword = passwordEncoder.encode(password);
            newUser.setPassword(encodePassword);
        }
        newUser = userRepository.save(newUser);
        return userMapper.toDTO(newUser);
//        return userRepository.save(newUser); //chuyen UserDTO -> User
    }

    @Override
    public String login(String email, String password) throws Exception { //Kieu String de tra ve token_key
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()) {
            throw new DataNotFoundException("Invalid email or password!");
        } 
//        return optionalUser.get(); //get() de lay ra doi tuong User
        User existingUser = optionalUser.get();
        //check password
        if(existingUser.getFacebookAccountId() == 0 && existingUser.getGoogleAccountId() == 0) {
            if(!passwordEncoder.matches(password, existingUser.getPassword())) {
                throw new BadCredentialsException("Wrong email or password!");
            }
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                email, password,
                existingUser.getAuthorities()
        );
        //Authenticate with Java Spring Security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }
}
