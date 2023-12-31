package com.example.ecommerce.controller;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService bookService;
    @GetMapping("cart")
    private String index(Model model) {
        var courses = cartService.getCartItems();
        double total = 0;
        for (var product : courses) {
            total += product.getBook().getPrice()*product.getQuantity();
        }
        if(courses.size() == 0) {
            model.addAttribute("check", false);
        } else {
            model.addAttribute("check", true);
        }
        model.addAttribute("total", total);
        model.addAttribute("courses", courses);
        return "Cart/index";
    }
    @GetMapping("/cart/delete/{id}")
    public String deleteItemCart(@PathVariable Long id) {
        cartService.remove(id);
        return "redirect:/cart";
    }

    @PostMapping("/cart/{id}")
    public String addToCart(@PathVariable Long id) {
        Product product = bookService.getBook(id);

        if (product != null) {
            cartService.addProduct(product);
        }
        return "redirect:/products";
    }
    @PostMapping("/cart/update")
    @ResponseBody
    public String updateCartItem(@RequestParam Long id, @RequestParam Integer quantity) {
        // Use the "id" and "quantity" parameters to update the cart item in the service
        cartService.update(id, quantity);
        return "Cart/index";
    }

}
