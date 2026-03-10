package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import id.ac.ui.cs.advprog.eshop.model.Payment; 
import static org.mockito.ArgumentMatchers.any; 
import static org.mockito.ArgumentMatchers.eq; 
import java.util.HashMap;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private PaymentService paymentService;

    private List<Order> mockOrders;

    @BeforeEach
    void setUp() {
        mockOrders = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        Product p = new Product();
        p.setProductId("p1");
        products.add(p);
        
        Order order = new Order("order-1", products, 1708560000L, "Nadine");
        mockOrders.add(order);
    }

    @Test
    void testCreateOrderPage() throws Exception {
        mockMvc.perform(get("/order/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/CreateOrder"));
    }

    @Test
    void testHistoryOrderPage() throws Exception {
        mockMvc.perform(get("/order/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/HistoryOrder"));
    }

    @Test
    void testHistoryOrderPost() throws Exception {
        when(orderService.findAllByAuthor("Nadine")).thenReturn(mockOrders);

        mockMvc.perform(post("/order/history")
                .param("author", "Nadine"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/ListOrder"))
                .andExpect(model().attributeExists("orders"))
                .andExpect(model().attributeExists("author"));
    }

    @Test
    void testPayOrderPage() throws Exception {
        Order order = mockOrders.get(0);
        when(orderService.findById("order-1")).thenReturn(order);

        mockMvc.perform(get("/order/pay/order-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/PaymentOrder"))
                .andExpect(model().attributeExists("order"));
    }

    @Test
    void testPayOrderPost() throws Exception {
        Order order = mockOrders.get(0);
        when(orderService.findById("order-1")).thenReturn(order);
        Payment mockPayment = new Payment("pay-1", order, "VOUCHER", new HashMap<>());
        when(paymentService.addPayment(eq(order), eq("VOUCHER"), any())).thenReturn(mockPayment);

        mockMvc.perform(post("/order/pay/order-1")
                .param("method", "VOUCHER")
                .param("voucherCode", "ESHOP1234ABC5678"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/PaymentResult"))
                .andExpect(model().attributeExists("payment"));
    }

    @Test
    void testCreateOrderPost() throws Exception {
        String author = "Nadine";
        when(orderService.findAllByAuthor(author)).thenReturn(mockOrders);
        mockMvc.perform(post("/order/create")
                .param("author", author))
                .andExpect(status().isOk())
                .andExpect(view().name("order/ListOrder"))
                .andExpect(model().attributeExists("orders"))
                .andExpect(model().attributeExists("author"))
                .andExpect(model().attribute("author", author));
        org.mockito.Mockito.verify(orderService, org.mockito.Mockito.times(1)).createOrder(any(Order.class));
    }
}