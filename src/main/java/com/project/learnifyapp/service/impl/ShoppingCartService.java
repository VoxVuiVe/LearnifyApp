package com.project.learnifyapp.service.impl;

import com.project.learnifyapp.dtos.CartItemDTO;
import com.project.learnifyapp.dtos.CourseDTO;
import com.project.learnifyapp.models.CartItem;
import com.project.learnifyapp.models.Course;
import com.project.learnifyapp.models.User;
import com.project.learnifyapp.repository.CartItemRepository;
import com.project.learnifyapp.repository.CourseRepository;
import com.project.learnifyapp.repository.UserRepository;
import com.project.learnifyapp.service.IShoppingCartService;
import com.project.learnifyapp.service.mapper.CartItemMapper;
import com.project.learnifyapp.service.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoppingCartService implements IShoppingCartService {
    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final CartItemRepository cartItemRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CartItemMapper cartItemMapper;
    private final CourseMapper courseMapper;
    private final CourseService courseService;

    @Override
    public List<CartItemDTO> saveShoppingCart(CartItemDTO request) {
        if (request.getUserId() == null) {
            log.debug("User not logged in");
            return Collections.emptyList();
        }

        String cartData = request.getCartData();
        if (cartData == null) {
            log.debug("No cart data provided");
            return Collections.emptyList();
        }

        String[] courseIds = cartData.split(",");
        List<Course> listCourse = new ArrayList<>();

        for (String courseId : courseIds) {
            try {
                Long courseIdLong = Long.valueOf(courseId);
                Course courseDTO = courseRepository.findById(courseIdLong).orElse(null);
                if (courseDTO != null) {
                    listCourse.add(courseDTO);
                }
            } catch (NumberFormatException e) {
                log.error("Invalid courseId: {}", courseId);
            }
        }

        List<CartItemDTO> newCartItems = new ArrayList<>();

        for (Course course : listCourse) {
            // Check if the course already exists in the cart
            CartItem existingCartItem = cartItemRepository.findByUserIdAndCourseId(request.getUserId(), course.getId());
            if (existingCartItem != null) {
                log.info("Course {} already exists in the cart", course.getId());
                continue;
            }

            // Create a new CartItemDTO for each unique course
            CartItemDTO cartItemDTO = CartItemDTO.builder()
                    .totalPrice(course.getPrice())
                    .userId(request.getUserId())
                    .build();

            // Update cartData with the new courseId
            cartData = updateCartData(cartData, course.getId());

            // Set the updated cartData to the CartItemDTO
            cartItemDTO.setCartData(cartData);

            CartItem ci = cartItemMapper.toEntity(cartItemDTO);
            newCartItems.add(cartItemMapper.toDTO(cartItemRepository.save(ci)));
        }

        return newCartItems;
    }

    private String updateCartData(String cartData, Long courseId) {
        // Parse the existing cartData and update it with the new courseId
        List<Long> courseIds = Arrays.stream(cartData.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());

        // Add the new courseId if it's not already in the list
        if (!courseIds.contains(courseId)) {
            courseIds.add(courseId);
        }

        // Convert the updated list of courseIds back to a String
        return courseIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CartItemDTO> findOneCartItemById(Long userId) {
        return cartItemRepository.findById(userId).map(cartItemMapper::toDTO);
    }

    @Override
    public Page<CartItemDTO> findAllPage(Long userId, PageRequest pageRequest) {
        Page<CartItem> cartItemPage = cartItemRepository.findAllByUserId(userId, pageRequest);
        return cartItemPage.map(this::convertToDTO);
    }

    private CartItemDTO convertToDTO(CartItem cartitem) {
        CartItemDTO dto = new CartItemDTO();
        dto.setId(cartitem.getId());
        dto.setTotalPrice(cartitem.getTotalPrice());
        String cartData = cartitem.getCartData();
        List<CourseDTO> courses = parseCartData(cartData);
        dto.setCourses(courses);

        User user = cartitem.getUser();
        if (user != null) {
            dto.setUserId(user.getId());
        }

        return dto;
    }

    private List<CourseDTO> parseCartData(String cartData) {
        List<CourseDTO> courses = new ArrayList<>();

        if (cartData != null && !cartData.isEmpty()) {
            String[] courseIds = cartData.split(",");
            for (String courseId : courseIds) {
                // Tạo đối tượng CourseDTO và thêm vào danh sách
                CourseDTO courseDTO = createCourseDTOFromCourseId(Long.parseLong(courseId));
                courses.add(courseDTO);
            }
        }

        return courses;
    }

    private CourseDTO createCourseDTOFromCourseId(Long courseId) {
        // Tìm kiếm và trả về thông tin chi tiết về khóa học dựa trên courseId
        // (Bạn cần triển khai logic này dựa trên cơ sở dữ liệu của bạn)
        // Giả sử có một phương thức trong service để lấy thông tin chi tiết của khóa học dựa trên courseId
        CourseDTO courseDTO = courseService.findOne(courseId);
        return courseDTO != null ? courseDTO : new CourseDTO();
    }


    @Override
    public void deleteCartItem(Long id) {
        try {
            Optional<CartItem> optionalCartItem = cartItemRepository.findById(id);
            if (optionalCartItem.isPresent()) {
                CartItem cartItem = optionalCartItem.get();
                cartItemRepository.deleteById(id);

                // Kiểm tra xem cartData có rỗng hay không
                if (cartItem.getCartData() == null || cartItem.getCartData().isEmpty()) {
                    // Nếu cartData rỗng, có thể thông báo cho người dùng ở đây
                    // Ví dụ: throw new EmptyCartException("Giỏ hàng trống");
                    // Trong trường hợp thông báo, bạn có thể tạo một Exception tùy chỉnh hoặc sử dụng một ngoại lệ có sẵn.
                } else {
                    // Nếu không rỗng, cập nhật lại cartData sau khi xóa course
                    String updatedCartData = updateCartDataAfterDelete(cartItem.getCartData(), id);
                    cartItem.setCartData(updatedCartData);
                    cartItemRepository.save(cartItem);
                }
            }
        } catch (Exception e) {
            log.debug("Failed to delete cartItem", e);
            throw new RuntimeException("Failed to delete cartItem: " + e.getMessage(), e);
        }

    }
    private String updateCartDataAfterDelete(String cartData, Long courseId) {
        List<CourseDTO> courses = parseCartData(cartData);

        // Loại bỏ courseId khỏi danh sách
        courses.removeIf(course -> Objects.equals(course.getId(), courseId));

        // Chuyển đổi danh sách đã cập nhật thành chuỗi
        return courses.stream()
                .map(course -> String.valueOf(course.getId()))
                .collect(Collectors.joining(","));
    }

}

