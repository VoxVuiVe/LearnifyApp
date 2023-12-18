package com.project.learnifyapp.repository;

import com.project.learnifyapp.models.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem , Long> {
//    List<CartItem> findAllByUserId(Long userId);
@Query(value = "SELECT * FROM cart_item ci WHERE ci.user_id = :keyword", nativeQuery = true)
List<CartItem> findAllByUserId(@Param("keyword") Long userId);

    @Query(value = "SELECT * FROM cart_item ci WHERE ci.user_id = :keyword", nativeQuery = true)
    CartItem findCartItemByUser(@Param("keyword") Long userId);

//    @Query(value = "SELECT * FROM cart_item c WHERE c.user_id = :keyword AND c.status = 'pending'", nativeQuery = true)
//    CartItem findUserIdAndStatus(@Param("keyword") Long userId);
//
//    Page<CartItem> findAllByUserId(Long userId, PageRequest pageRequest);
//    default CartItem findByUserIdAndCourseId(Long userId, Long courseId) {
//        List<CartItem> cartItems = findAllByUserId(userId);
//
//        for (CartItem cartItem : cartItems) {
//            List<Long> courseIds = parseCartData(cartItem.getCartData());
//            if (courseIds.contains(courseId)) {
//                return cartItem;
//            }
//        }
//
//        return null;
//    }
//
//    List<CartItem> findAllByUserId(Long userId);
//
//    default List<Long> parseCartData(String cartData) {
//        List<Long> courseIds = new ArrayList<>();
//
//        if (cartData != null && !cartData.isEmpty()) {
//            String[] courseIdStrings = cartData.split(",");
//            for (String courseIdString : courseIdStrings) {
//                try {
//                    Long courseId = Long.valueOf(courseIdString);
//                    courseIds.add(courseId);
//                } catch (NumberFormatException e) {
//                    // Handle invalid courseIdString
//                }
//            }
//        }
//
//        return courseIds;
//    }
}
