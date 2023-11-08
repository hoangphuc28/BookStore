package com.example.ecommerce.controller;

import com.example.ecommerce.model.Customer;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class PaymentController {
    @Autowired
    private PaypalService paypalService;

    @Autowired
    private CartService cartService;

    @Autowired
    private TokenService tokenService;
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/payment/failed")
    private String failedPayment(@RequestParam String courses) {
        return "Notify/failed-payment";
    }
    @GetMapping("/payment/success")
    private String successPayment(@RequestParam String token, @RequestParam String courses, Principal principal) {
        var res = tokenService.decryptTokenCourses(courses);
        paypalService.capturePayment(token);
        var user = userService.getUserByString(principal.getName());
        cartService.removeAll();
        return "Notify/success-payment";
    }
    @PostMapping("/payment")
    private String payment(@ModelAttribute("customer") Customer customer, HttpSession session) throws IOException {
        System.out.println(customer);
//        Customer c = customerService.createCustomer(customer);
//        var courses = cartService.getCartItems();
//        double total = 0;
//        for (var product : courses) {
//            total+= product.getBook().getPrice()*product.getQuantity();
//            product.getBook().setQuantity(product.getBook().getQuantity()-product.getQuantity());
//            productService.update(product.getBook());
//        }
//        Order a = new Order();
//        a.setTotalAmount(BigDecimal.valueOf(total));
//        a.setOrderDate(new Date());
//        a.setCustomer(c);
//       Order order =  orderService.createOrder(a);
//
//        var token = tokenService.generateTokenCourses(order.getId().toString());
//        var data = paypalService.createJsonPayload(Double.valueOf(total), "http://localhost:8080/payment/success?courses=" +token, "http://localhost:8080/payment/failed" +token);
//        var url = (paypalService.getApproveLink(paypalService.createOrder(data)));
       return "redirect:";
    }

}
