package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
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

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

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
                .andExpect(view().name("order/CreateOrder")); // Disesuaikan
    }

    @Test
    void testHistoryOrderPage() throws Exception {
        mockMvc.perform(get("/order/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/HistoryOrder")); // Disesuaikan
    }

    @Test
    void testHistoryOrderPost() throws Exception {
        when(orderService.findAllByAuthor("Nadine")).thenReturn(mockOrders);

        mockMvc.perform(post("/order/history")
                .param("author", "Nadine"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/ListOrder")) // Disesuaikan
                .andExpect(model().attributeExists("orders"))
                .andExpect(model().attributeExists("author"));
    }
}