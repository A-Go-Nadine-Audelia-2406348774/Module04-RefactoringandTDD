package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    private Payment mockPayment;
    private List<Payment> mockPaymentList;

    @BeforeEach
    void setUp() {
        Order mockOrder = new Order("order-1", new ArrayList<>(), 1708560000L, "Nadine");
        mockPayment = new Payment("pay-1", mockOrder, "BANK_TRANSFER", new HashMap<>());
        mockPaymentList = new ArrayList<>();
        mockPaymentList.add(mockPayment);
    }

    @Test
    void testPaymentDetailFormPage() throws Exception {
        mockMvc.perform(get("/payment/detail"))
                .andExpect(status().isOk())
                .andExpect(view().name("payment/PaymentDetailForm"));
    }

    @Test
    void testPaymentDetailPage() throws Exception {
        when(paymentService.getPayment("pay-1")).thenReturn(mockPayment);

        mockMvc.perform(get("/payment/detail/pay-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("payment/PaymentDetail"))
                .andExpect(model().attributeExists("payment"));
    }

    @Test
    void testAdminListPaymentsPage() throws Exception {
        when(paymentService.getAllPayments()).thenReturn(mockPaymentList);

        mockMvc.perform(get("/payment/admin/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("payment/AdminPaymentList"))
                .andExpect(model().attributeExists("payments"));
    }

    @Test
    void testAdminDetailPaymentPage() throws Exception {
        when(paymentService.getPayment("pay-1")).thenReturn(mockPayment);

        mockMvc.perform(get("/payment/admin/detail/pay-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("payment/AdminPaymentDetail"))
                .andExpect(model().attributeExists("payment"));
    }

    @Test
    void testAdminSetPaymentStatusPost() throws Exception {
        when(paymentService.getPayment("pay-1")).thenReturn(mockPayment);

        mockMvc.perform(post("/payment/admin/set-status/pay-1")
                .param("status", "SUCCESS"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/payment/admin/list"));
    }
}