package com.example.ecommerce.controller.admin;

import com.example.ecommerce.model.Order;
import com.example.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class AdminOrderController {
    @Autowired
    private OrderService orderService;
    @GetMapping("/admin/order")
    public String showOrders(@RequestParam(name = "startDate", defaultValue = "2023-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                             @RequestParam(name = "endDate", defaultValue = "2023-12-31") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                             Model model) {
        System.out.println(startDate);
        // Set default values if the parameters are not provided
        if (startDate == null) {
            // Set the default start date to one month ago
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -1);
            startDate = calendar.getTime();
        }
        if (endDate == null) {
            // Set the default end date to the current date
            endDate = new Date();
        }

        List<Order> orders = orderService.getOrdersBetweenDates(startDate, endDate);
        model.addAttribute("orders", orders);
        model.addAttribute("totalOrder", orders.size());
        return "Admin/order/index";
    }


}
