package com.project.learnifyapp.service.impl;

import com.project.learnifyapp.components.JwtTokenUtil;
import com.project.learnifyapp.dtos.UserImageDTO;
import com.project.learnifyapp.dtos.UserDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.exceptions.InvalidParamException;
import com.project.learnifyapp.exceptions.PermissionDeniedException;
import com.project.learnifyapp.models.Course;
import com.project.learnifyapp.models.UserImage;
import com.project.learnifyapp.models.Role;
import com.project.learnifyapp.models.User;
import com.project.learnifyapp.repository.RoleRepository;
import com.project.learnifyapp.repository.UserImageRepository;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;

    private final UserImageRepository userImageRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtil jwtTokenUtil;

    private final AuthenticationManager authenticationManager;

    private final UserMapper userMapper;

    private static String UPLOADS_FOLDER = "uploads";

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

    @Override
    public UserImage createUserImage(Long userId, UserImageDTO userImageDTO) throws Exception {
        User existingUser = userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find product with id: "+userImageDTO.getProductId()));
        UserImage newUserImage = UserImage.builder()
                .user(existingUser)
                .imageUrl(userImageDTO.getImageUrl())
                .build();
        //Ko cho insert quá 5 ảnh cho 1 sản phẩm
        int size = userImageRepository.findByUserId(userId).size();
        if(size >= UserImage.MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new InvalidParamException(
                    "Number of images must be <= "
                            +UserImage.MAXIMUM_IMAGES_PER_PRODUCT);
        }
        if (existingUser.getImageUrl() == null ) {
            existingUser.setImageUrl(newUserImage.getImageUrl());
        }
        userRepository.save(existingUser);
        return userImageRepository.save(newUserImage);
    }

    @Override
    public User getUserById(Long userId) throws Exception {
        Optional<User> optionalUser = userRepository.getDetailUser(userId);
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new DataNotFoundException("Cannot find user with ID: " + userId);
    }

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    @Override
    public String storeFile(MultipartFile file) throws IOException {
        if (!isImageFile(file) || file.getOriginalFilename() == null) {
            throw new IOException("Invalid image format");
        }
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        // Thêm UUID vào trước tên file để đảm bảo tên file là duy nhất
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        // Đường dẫn đến thư mục mà bạn muốn lưu file
        java.nio.file.Path uploadDir = Paths.get(UPLOADS_FOLDER);
        // Kiểm tra và tạo thư mục nếu nó không tồn tại
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // Đường dẫn đầy đủ đến file
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        // Sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }

    @Override
    public void deleteFile(String filename) throws IOException {
        // Đường dẫn đến thư mục chứa file
        java.nio.file.Path uploadDir = Paths.get(UPLOADS_FOLDER);
        // Đường dẫn đầy đủ đến file cần xóa
        java.nio.file.Path filePath = uploadDir.resolve(filename);

        // Kiểm tra xem file tồn tại hay không
        if (Files.exists(filePath)) {
            // Xóa file
            Files.delete(filePath);
        } else {
            throw new FileNotFoundException("File not found: " + filename);
        }
    }
}
