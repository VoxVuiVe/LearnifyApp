package com.project.learnifyapp.service.impl;

import com.project.learnifyapp.dtos.PaymentDTO;
import com.project.learnifyapp.dtos.PaymentHistoryDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.models.Payment;
import com.project.learnifyapp.models.PaymentStatus;
import com.project.learnifyapp.models.User;
import com.project.learnifyapp.models.UserCourse;
import com.project.learnifyapp.repository.PaymentRepository;
import com.project.learnifyapp.repository.UserCourseRepository;
import com.project.learnifyapp.repository.UserRepository;
import com.project.learnifyapp.service.IPaymentService;
import com.project.learnifyapp.service.VNPayService;
import com.project.learnifyapp.service.mapper.PaymentMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {

    private final UserRepository userRepository;

    private final PaymentRepository paymentRepository;

    private final PaymentHistoryService paymentHistoryService;

    private final UserCourseRepository userCourseRepository;

    private final PaymentMapper paymentMapper;

    private final ModelMapper modelMapper;

    private final VNPayService vnPayService;

    @Override
    public PaymentDTO createPayment(PaymentDTO paymentDTO) throws Exception {
        User existingUser = userRepository.findById(paymentDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find user with ID: " + paymentDTO.getUserId()));

        // Map từ PaymentDTO sang Payment entity
        Payment payment = paymentMapper.toEntity(paymentDTO);
        payment.setUser(existingUser);
        payment.setPaymentDate(new Date());
        payment.setStatus(PaymentStatus.PENDING);

        // Tạo đơn hàng trên VNPay và lấy URL thanh toán
        String vnPayUrl = vnPayService.createOrder(payment);

        payment = paymentRepository.save(payment);

        // Tạo PaymentHistory
        PaymentHistoryDTO paymentHistoryDTO = new PaymentHistoryDTO();
        paymentHistoryDTO.setPaymentId(payment.getId());
        paymentHistoryDTO.setStatus(payment.getStatus());
        paymentHistoryDTO.setTransactionDate(payment.getPaymentDate().toString());
        paymentHistoryDTO.setTotalMoney(payment.getTotalMoney());
        paymentHistoryService.createPaymentHistory(paymentHistoryDTO);

        // Tạo một PaymentDTO mới để trả về, bao gồm cả URL thanh toán từ VNPay
        PaymentDTO paymentResponse = paymentMapper.toDTO(payment);
        paymentResponse.setPaymentMethod(vnPayUrl);

        return paymentResponse;
    }
    @Override
    public int orderReturn(HttpServletRequest request) {
        // Gọi phương thức orderReturn của VNPayService
        int result = vnPayService.orderReturn(request);

        // Kiểm tra kết quả
        if (result == 1) {
            // Nếu giao dịch thành công, cập nhật UserCourse
            String vnp_TxnRef = request.getParameter("vnp_TxnRef");
            Payment payment = paymentRepository.findById(Long.parseLong(vnp_TxnRef)).orElse(null);
            if (payment != null) {
                UserCourse userCourse = new UserCourse();
                userCourse.setUser(payment.getUser());
                userCourse.setCourse(payment.getCourse());
                userCourse.setEnrollmentDate(payment.getPaymentDate());
                userCourse.setPaymentStatus(true);

                userCourseRepository.save(userCourse);
            }
        }

        return result;
    }

    @Override
    public PaymentDTO getPayment(Long id) {
        Payment paymentResponse = paymentRepository.findById(id).orElse(null);
        return paymentMapper.toDTO(paymentResponse);
    }

    @Override
    public PaymentDTO updateOrder(Long id, PaymentDTO paymentDTO) throws DataNotFoundException {
        Payment existingPayment = paymentRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException("Cannot find payment with id: " + id));
        User existingUser = userRepository.findById(
                paymentDTO.getUserId()).orElseThrow(() ->
                new DataNotFoundException("Cannot find user with id: " + id));

        // Tạo một luồng bảng ánh xạ riêng để kiểm soát việc ánh xạ
        modelMapper.typeMap(PaymentDTO.class, Payment.class)
                .addMappings(mapper -> mapper.skip(Payment::setId));

        modelMapper.map(paymentDTO, existingPayment);
        existingPayment.setUser(existingUser);
        existingPayment = paymentRepository.save(existingPayment);
        return paymentMapper.toDTO(existingPayment);
    }

    @Override
    public void deletePayment(Long id) {
        Payment existingPayment = paymentRepository.findById(id).orElse(null);
        if(existingPayment != null) {
            paymentRepository.save(existingPayment);
        }
    }

    @Override
    public List<PaymentDTO> findByUserId(Long userId) {
        List<Payment> list = paymentRepository.findByUserId(userId);
        return paymentMapper.toDTO(list);
    }
}
