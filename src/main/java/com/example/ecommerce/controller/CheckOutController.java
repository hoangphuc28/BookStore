package com.example.ecommerce.controller;

import com.example.ecommerce.model.Customer;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.*;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Controller
public class CheckOutController {
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private EmailService emailService;
    @PostMapping("/checkout")
    public String checkout(Model model, @RequestParam String total) {
        System.out.println(total);
        model.addAttribute("total", total);
        model.addAttribute("order", new Order());
        model.addAttribute("customer", new Customer());
        return "checkout/index";
    }
    @PostMapping("/checkout/confirm")
    public String checkoutConfirm(Model model, @ModelAttribute("customer") Customer customer) throws MessagingException, IOException {
       Customer c = customerService.createCustomer(customer);
        var courses = cartService.getCartItems();
        double total = 0;
        for (var product : courses) {
            total+= product.getBook().getPrice()*product.getQuantity();
            product.getBook().setQuantity(product.getBook().getQuantity()-product.getQuantity());
            productService.update(product.getBook());
        }
        Order a = new Order();
        a.setTotalAmount(BigDecimal.valueOf(total));
        a.setOrderDate(new Date());
        a.setCustomer(c);
        orderService.createOrder(a);
        cartService.removeAll();
        emailService.sendOrderDetail(c.getEmail(), "Order Tracking", a);
        model.addAttribute("customer", c);
        return "order/index";
    }
}
