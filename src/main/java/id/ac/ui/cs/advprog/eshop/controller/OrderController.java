package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/create")
    public String createOrderPage() {
        return "order/CreateOrder";
    }

    @GetMapping("/history")
    public String historyOrderPage() {
        return "order/HistoryOrder";
    }

    @PostMapping("/history")
    public String historyOrderPost(@RequestParam String author, Model model) {
        List<Order> orders = orderService.findAllByAuthor(author);
        model.addAttribute("orders", orders);
        model.addAttribute("author", author);
        return "order/ListOrder";
    }

    @Autowired
    private id.ac.ui.cs.advprog.eshop.service.PaymentService paymentService;

    @GetMapping("/pay/{orderId}")
    public String payOrderPage(@PathVariable String orderId, Model model) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        return "order/PaymentOrder";
    }

    @PostMapping("/pay/{orderId}")
    public String payOrderPost(@PathVariable String orderId, 
                               @RequestParam String method, 
                               @RequestParam java.util.Map<String, String> allParams, 
                               Model model) {
        Order order = orderService.findById(orderId);
        id.ac.ui.cs.advprog.eshop.model.Payment payment = paymentService.addPayment(order, method, allParams);
        model.addAttribute("payment", payment);
        return "order/PaymentResult";
    }
}