package com.project.learnifyapp.service.impl;

import com.project.learnifyapp.dtos.PaymentDTO;
import com.project.learnifyapp.dtos.PaymentHistoryDTO;
import com.project.learnifyapp.dtos.UserCourseDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.models.*;
import com.project.learnifyapp.repository.*;
import com.project.learnifyapp.service.IPaymentHistoryService;
import com.project.learnifyapp.service.mapper.PaymentHistoryMapper;
import com.project.learnifyapp.service.mapper.UserCourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentHistoryService implements IPaymentHistoryService {
    private final PaymentHistoryRepository paymentHistoryRepository;

    private final PaymentRepository paymentRepository;

    private final CourseRepository courseRepository;

    private final UserCourseRepository userCourseRepository;

    private final PaymentHistoryMapper paymentHistoryMapper;

    private final UserCourseMapper userCourseMapper;

    @Override
    public PaymentHistoryDTO createPaymentHistory(PaymentHistoryDTO paymentHistoryDTO) throws Exception {
        Payment payment = paymentRepository.findById(paymentHistoryDTO.getPaymentId()).orElseThrow(() ->
                new DataNotFoundException("Cannot find Payment with ID: " + paymentHistoryDTO.getPaymentId()));

        Course course = courseRepository.findById(paymentHistoryDTO.getCourseId()).orElseThrow(() ->
                new DataNotFoundException("Cannot find Course with ID: " + paymentHistoryDTO.getCourseId()));

        course.setEnrollmentCount(course.getEnrollmentCount() + 1);
        courseRepository.save(course);

        PaymentHistory paymentHistory = paymentHistoryMapper.toEntity(paymentHistoryDTO);
        paymentHistory.setCourse(course);
        paymentHistory.setPayment(payment);
        paymentHistory.setTransactionId(paymentHistoryDTO.generateTransactionId());
        paymentHistory = paymentHistoryRepository.save(paymentHistory);

        // is the payment is successful, create a new UserCourse
        if (payment.getStatus().equals(PaymentStatus.SUCCESS)){
            UserCourse userCourse = new UserCourse();
            userCourse.setUser(paymentHistory.getPayment().getUser());
            userCourse.setCourse(course);
            userCourse.setPaymentStatus(true);
            userCourseRepository.save(userCourse);
        }
        return paymentHistoryMapper.toDTO(paymentHistory);
    }

    @Override
    public PaymentHistoryDTO getPaymentHistory(Long id) {
        PaymentHistory paymentHistoryResponse = paymentHistoryRepository.findById(id).orElse(null);
        return paymentHistoryMapper.toDTO(paymentHistoryResponse);
    }

    @Override
    public void deleteById(Long id) {
        paymentHistoryRepository.deleteById(id);
    }

    //Có thể sửa lại là findByUserId thay vì findByPaymentId
    @Override
    public List<PaymentHistory> findByPaymentId(Long paymentId) {
        return paymentHistoryRepository.findByPaymentId(paymentId);
    }

//    @Override
//    public PaymentHistoryDTO updatePaymentHistory(Long id, PaymentHistoryDTO paymentHistoryDTO) throws DataNotFoundException {
//        PaymentHistory existingPaymentHistory = paymentHistoryRepository.findById(id).orElseThrow(() ->
//                new DataNotFoundException("Cannot find Payment History with ID: " + id));
//        Payment existingPayment = paymentRepository.findById(paymentHistoryDTO.getPaymentId()).orElseThrow(() ->
//                new DataNotFoundException("Cannot find Payment History with ID: " + paymentHistoryDTO.getPaymentId()));
//
//        Course existingCourse = courseRepository.findById(paymentHistoryDTO.getPaymentId()).orElseThrow(() ->
//                new DataNotFoundException("Cannot find Payment History with ID: " + paymentHistoryDTO.getPaymentId()));
//
//        existingPaymentHistory.setPayment(existingPayment);
//        existingPaymentHistory.setCourse(existingCourse);
//        existingPaymentHistory.setTotalMoney();
//        paymentHistoryRepository.save(existingPaymentHistory);
//        return paymentHistoryMapper.toDTO(existingPaymentHistory);
//    }
}
