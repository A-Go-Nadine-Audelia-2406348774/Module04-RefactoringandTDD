package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/order")
public class OrderController {

    private static final String CREATE_ORDER_VIEW = "order/CreateOrder";
    private static final String HISTORY_ORDER_VIEW = "order/HistoryOrder";
    private static final String LIST_ORDER_VIEW = "order/ListOrder";
    private static final String PAYMENT_ORDER_VIEW = "order/PaymentOrder";
    private static final String PAYMENT_RESULT_VIEW = "order/PaymentResult";

    private static final String DUMMY_PRODUCT_ID = "p-dummy";
    private static final String DUMMY_PRODUCT_NAME = "Product Demo";
    private static final int DUMMY_PRODUCT_QUANTITY = 1;

    private final OrderService orderService;
    private final PaymentService paymentService;

    public OrderController(OrderService orderService, PaymentService paymentService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    @GetMapping("/create")
    public String createOrderPage() {
        return CREATE_ORDER_VIEW;
    }

    @GetMapping("/history")
    public String historyOrderPage() {
        return HISTORY_ORDER_VIEW;
    }

    @PostMapping("/history")
    public String historyOrderPost(@RequestParam String author, Model model) {
        return renderOrderHistory(author, model);
    }

    @GetMapping("/pay/{orderId}")
    public String payOrderPage(@PathVariable String orderId, Model model) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        return PAYMENT_ORDER_VIEW;
    }

    @PostMapping("/pay/{orderId}")
    public String payOrderPost(@PathVariable String orderId,
                               @RequestParam String method,
                               @RequestParam Map<String, String> allParams,
                               Model model) {
        Order order = orderService.findById(orderId);
        Payment payment = paymentService.addPayment(order, method, allParams);
        model.addAttribute("payment", payment);
        return PAYMENT_RESULT_VIEW;
    }

    @PostMapping("/create")
    public String createOrderPost(@RequestParam String author, Model model) {
        Order newOrder = buildDummyOrder(author);
        orderService.createOrder(newOrder);
        return renderOrderHistory(author, model);
    }

    private Order buildDummyOrder(String author) {
        return new Order(
                UUID.randomUUID().toString(),
                createDummyProducts(),
                System.currentTimeMillis(),
                author
        );
    }

    private List<Product> createDummyProducts() {
        List<Product> products = new ArrayList<>();

        Product dummyProduct = new Product();
        dummyProduct.setProductId(DUMMY_PRODUCT_ID);
        dummyProduct.setProductName(DUMMY_PRODUCT_NAME);
        dummyProduct.setProductQuantity(DUMMY_PRODUCT_QUANTITY);

        products.add(dummyProduct);
        return products;
    }

    private String renderOrderHistory(String author, Model model) {
        List<Order> orders = orderService.findAllByAuthor(author);
        model.addAttribute("orders", orders);
        model.addAttribute("author", author);
        return LIST_ORDER_VIEW;
    }
}