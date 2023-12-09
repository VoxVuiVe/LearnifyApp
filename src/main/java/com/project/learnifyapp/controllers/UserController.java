package com.project.learnifyapp.controllers;

import com.project.learnifyapp.components.LocalizationUtils;
import com.project.learnifyapp.dtos.UserDTO;
import com.project.learnifyapp.dtos.UserImageDTO;
import com.project.learnifyapp.dtos.UserLoginDTO;
import com.project.learnifyapp.models.User;
import com.project.learnifyapp.models.UserImage;
import com.project.learnifyapp.repository.UserRepository;
import com.project.learnifyapp.responses.LoginResponse;
import com.project.learnifyapp.responses.UserListResponse;
import com.project.learnifyapp.responses.UserResponse;
import com.project.learnifyapp.service.IUserService;
import com.project.learnifyapp.service.impl.UserService;
import com.project.learnifyapp.utils.MessageKeys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final LocalizationUtils localizationUtils;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO,
                                        BindingResult bindingResult) {
        try {
            if(bindingResult.hasErrors()) {
                List<String> errorMessages = bindingResult.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
            }
            if(!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
                return ResponseEntity.badRequest().body(localizationUtils.getLocalizedMessage(MessageKeys.PASSWORD_NOT_MATCH));
            }
            UserDTO newUser = userService.createUser(userDTO);
            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<UserListResponse> getAllUsers(@RequestParam(defaultValue = "") String keyword,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "12")  int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("id").ascending());
        logger.info(String.format("keyword = %s, page = %d, limit = %d",
                keyword, page, limit));
        Page<UserResponse> usersPage = userService.getAllUsers(keyword, pageRequest);
        int totalPages = usersPage.getTotalPages();
        List<UserResponse> users = usersPage.getContent();
        return ResponseEntity.ok(UserListResponse
                        .builder()
                        .users(users)
                        .totalPages(totalPages)
                        .build());
    }

    @PostMapping(value = "/uploads/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    //POST http://localhost:8080/v1/api/user/profile
    public ResponseEntity<?> uploadImages(
            @PathVariable("id") Long userId,
            @ModelAttribute("files") List<MultipartFile> files
    ){
        try {
            User existingUser = userService.getUserById(userId);
            files = files == null ? new ArrayList<>() : files;
            if(files.size() > UserImage.MAXIMUM_IMAGES_PER_PRODUCT) {
                return ResponseEntity.badRequest().body(localizationUtils
                        .getLocalizedMessage(MessageKeys.UPLOAD_IMAGES_MAX_1));
            }
            List<UserImage> productImages = new ArrayList<>();
            for (MultipartFile file : files) {
                if(file.getSize() == 0) {
                    continue;
                }
                // Kiểm tra kích thước file và định dạng
                if(file.getSize() > 10 * 1024 * 1024) { // Kích thước > 10MB
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body(localizationUtils
                                    .getLocalizedMessage(MessageKeys.UPLOAD_IMAGES_FILE_LARGE));
                }
                String contentType = file.getContentType();
                if(contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body(localizationUtils.getLocalizedMessage(MessageKeys.UPLOAD_IMAGES_FILE_MUST_BE_IMAGE));
                }
                // Lưu file và cập nhật thumbnail trong DTO
                String filename = userService.storeFile(file); // Thay thế hàm này với code của bạn để lưu file
                //lưu vào đối tượng product trong DB
                UserImage productImage = userService.createUserImage(
                        existingUser.getId(),
                        UserImageDTO.builder()
                                .imageUrl(filename)
                                .build()
                );
                productImages.add(productImage);
            }

            return ResponseEntity.ok().body(productImages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO) {
        //Kiểm tra thông tin đăng nhập và sinh TOKEN
        try {
            String token = userService.login(userLoginDTO.getEmail(),
                    userLoginDTO.getPassword(),
                    userLoginDTO.getRoleId());
            // Tra ve token trong response
            return ResponseEntity.ok(LoginResponse.builder().message(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_SUCCESSFULLY))
                    .token(token).build());
        } catch (Exception e) {
            return ResponseEntity.ok(LoginResponse.builder().message(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_FAILED, e.getMessage())).build());
        }
    }
}
