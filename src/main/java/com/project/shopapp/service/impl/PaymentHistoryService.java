package com.project.shopapp.service.impl;

import com.project.shopapp.dtos.PaymentHistoryDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Course;
import com.project.shopapp.models.PaymentHistory;
import com.project.shopapp.models.User;
import com.project.shopapp.repository.CourseRepository;
import com.project.shopapp.repository.PaymentHistoryRepository;
import com.project.shopapp.repository.UserRepository;
import com.project.shopapp.service.IPaymentHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentHistoryService implements IPaymentHistoryService {
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public PaymentHistory createPaymentHistory(PaymentHistoryDTO paymentHistoryDTO) throws DataNotFoundException {
        Long courseId = paymentHistoryDTO.getCourseId();
        Long userId = paymentHistoryDTO.getUserId();

        // Kiểm tra sự tồn tại của Course và User
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new DataNotFoundException("Course không tồn tại"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User không tồn tại"));

        // Convert PaymentHistoryDTO -> PaymentHistory
        PaymentHistory newPaymentHistory = PaymentHistory.builder()
                .totalMoney(paymentHistoryDTO.getTotalMoney())
                .numberOfCourse(paymentHistoryDTO.getNumberOfCourse())
                .transactionStatus(paymentHistoryDTO.getTransactionStatus())
                .paymentDate(LocalDateTime.now())
                .paymentMethod(paymentHistoryDTO.getPaymentMethod())
                .course(course)
                .user(user)
                .build();

        return paymentHistoryRepository.save(newPaymentHistory);
    }

    @Override
    public PaymentHistory getPaymentHistoryById(Long paymentHistoryId) throws DataNotFoundException {
        return paymentHistoryRepository.findById(paymentHistoryId)
                .orElseThrow(() -> new DataNotFoundException("Payment History không tồn tại!"));
    }

    @Override
    public PaymentHistory updatePaymentHistory(Long paymentHistoryId, PaymentHistoryDTO updatedData) throws DataNotFoundException {
        PaymentHistory paymentHistory = getPaymentHistoryById(paymentHistoryId);

        paymentHistory.setTotalMoney(updatedData.getTotalMoney());
        paymentHistory.setNumberOfCourse(updatedData.getNumberOfCourse());
        paymentHistory.setTransactionStatus(updatedData.getTransactionStatus());
        paymentHistory.setPaymentDate(updatedData.getPaymentDate());
        paymentHistory.setPaymentMethod(updatedData.getPaymentMethod());

        return paymentHistoryRepository.save(paymentHistory);
    }

    @Override
    public void deletePaymentHistory(Long paymentHistoryId) {
        paymentHistoryRepository.deleteById(paymentHistoryId);
    }
}
