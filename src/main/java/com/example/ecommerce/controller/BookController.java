package com.example.ecommerce.controller;

import com.example.ecommerce.model.Book;
import com.example.ecommerce.service.BookService;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.CategoryService;
import com.example.ecommerce.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BookController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CartService cartService;
    @Autowired
    private BookService bookService;

    @Autowired
    private CustomUserDetailsService userService;
    @GetMapping("/books")
    private String index(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "0") Long category, Model model, Principal principal) {
        PageRequest pageable = PageRequest.of(page-1, 8);
        Page<Book> courses = bookService.paginateCourse(category, pageable);

//        var cartItems = cartService.getCartItems().stream()
//                .map(Book::getId)
//                .collect(Collectors.toList());;
//        model.addAttribute("cartItems", cartItems);
        model.addAttribute("filterCategory", category);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("courses", courses.getContent());
        model.addAttribute("currentPage", page-1);
        model.addAttribute("totalPages", courses.getTotalPages()-1);

        return "Book/index";
    }
}
