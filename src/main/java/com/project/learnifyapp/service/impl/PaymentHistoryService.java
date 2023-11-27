package com.project.learnifyapp.service.impl;

import com.project.learnifyapp.dtos.PaymentHistoryDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.models.Course;
import com.project.learnifyapp.models.Payment;
import com.project.learnifyapp.models.PaymentHistory;
import com.project.learnifyapp.models.User;
import com.project.learnifyapp.repository.CourseRepository;
import com.project.learnifyapp.repository.PaymentHistoryRepository;
import com.project.learnifyapp.repository.PaymentRepository;
import com.project.learnifyapp.repository.UserRepository;
import com.project.learnifyapp.service.IPaymentHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentHistoryService implements IPaymentHistoryService {
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final CourseRepository courseRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public PaymentHistory createPaymentHistory(PaymentHistoryDTO paymentHistoryDTO) throws DataNotFoundException {
        Long courseId = paymentHistoryDTO.getCourseId();
        Long paymentId = paymentHistoryDTO.getPaymentId();

        // Kiểm tra sự tồn tại của Course và User
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new DataNotFoundException("Course không tồn tại"));

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new DataNotFoundException("User không tồn tại"));

        // Convert PaymentHistoryDTO -> PaymentHistory
        PaymentHistory newPaymentHistory = PaymentHistory.builder()
                .totalMoney(paymentHistoryDTO.getTotalMoney())
                .numberOfCourse(paymentHistoryDTO.getNumberOfCourse())
                .transactionId(paymentHistoryDTO.getTransactionId())
                .price(paymentHistoryDTO.getPrice())
                .course(course)
                .payment(payment)
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
        paymentHistory.setTransactionId(updatedData.getTransactionId());
        paymentHistory.setPrice(updatedData.getPrice());

        return paymentHistoryRepository.save(paymentHistory);
    }

    @Override
    public void deletePaymentHistory(Long paymentHistoryId) {
        paymentHistoryRepository.deleteById(paymentHistoryId);
    }
}
