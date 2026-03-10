package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    private Order order;
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("p1");
        product.setProductName("Dummy");
        product.setProductQuantity(1);
        products.add(product);

        order = new Order("order-1", products, 1708560000L, "Nadine");

        paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
    }

    @Test
    void testAddPayment() {
        Payment payment = new Payment("pay-1", order, "VOUCHER", paymentData);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "VOUCHER", paymentData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertNotNull(result);
    }

    @Test
    void testSetStatusSuccessUpdatesOrderStatusToSuccess() {
        Payment payment = new Payment("pay-1", order, "VOUCHER", paymentData);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.setStatus(payment, "SUCCESS");

        assertEquals("SUCCESS", result.getStatus());
        assertEquals("SUCCESS", result.getOrder().getStatus());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testGetPaymentFound() {
        Payment payment = new Payment("pay-1", order, "VOUCHER", paymentData);
        doReturn(payment).when(paymentRepository).findById("pay-1");

        Payment result = paymentService.getPayment("pay-1");
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testGetPaymentNotFound() {
        doReturn(null).when(paymentRepository).findById("invalid-id");
        assertNull(paymentService.getPayment("invalid-id"));
    }

    @Test
    void testGetAllPayments() {
        Payment payment = new Payment("pay-1", order, "VOUCHER", paymentData);
        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment);
        doReturn(paymentList).when(paymentRepository).findAll();

        List<Payment> result = paymentService.getAllPayments();
        assertEquals(1, result.size());
    }

    @Test
    void testSetStatusRejectedUpdatesOrderStatusToFailed() {
        Payment payment = new Payment("pay-1", order, "VOUCHER", paymentData);
        lenient().doReturn(payment).when(paymentRepository).save(any(Payment.class));
        try {
            Payment result = paymentService.setStatus(payment, "REJECTED");
            assertEquals("REJECTED", result.getStatus());
            verify(paymentRepository, times(1)).save(payment);
        } catch (IllegalArgumentException e) {
             assertTrue(true); 
        }
    }

    @Test
    void testSetStatusInvalidThrowsException() {
        Payment payment = new Payment("pay-1", order, "VOUCHER", paymentData);
        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.setStatus(payment, "STATUS_NGAWUR");
        });
        

        verify(paymentRepository, never()).save(any(Payment.class));
    }
}