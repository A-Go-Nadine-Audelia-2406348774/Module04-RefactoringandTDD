package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final String PAYMENT_STATUS_SUCCESS = "SUCCESS";
    private static final String PAYMENT_STATUS_REJECTED = "REJECTED";
    private static final String INVALID_PAYMENT_STATUS_MESSAGE = "Invalid payment status";

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        Payment payment = new Payment(UUID.randomUUID().toString(), order, method, paymentData);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        String validatedStatus = validateStatus(status);
        payment.setStatus(validatedStatus);
        payment.getOrder().setStatus(resolveOrderStatus(validatedStatus));
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    private String validateStatus(String status) {
        if (PAYMENT_STATUS_SUCCESS.equals(status) || PAYMENT_STATUS_REJECTED.equals(status)) {
            return status;
        }
        throw new IllegalArgumentException(INVALID_PAYMENT_STATUS_MESSAGE);
    }

    private String resolveOrderStatus(String paymentStatus) {
        return switch (paymentStatus) {
            case PAYMENT_STATUS_SUCCESS -> OrderStatus.SUCCESS.getValue();
            case PAYMENT_STATUS_REJECTED -> OrderStatus.FAILED.getValue();
            default -> throw new IllegalArgumentException(INVALID_PAYMENT_STATUS_MESSAGE);
        };
    }
}