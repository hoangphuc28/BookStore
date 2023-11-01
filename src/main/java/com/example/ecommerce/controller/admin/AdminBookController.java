package com.example.ecommerce.controller.admin;

import com.example.ecommerce.model.Book;
import com.example.ecommerce.service.BookService;
import com.example.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Controller
public class AdminBookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("/admin/books")
    public String Home(Model model) {
        List<Book> books = bookService.getAllBook();
            model.addAttribute("books", books);
        return "Admin/Book/index";
    }
    @GetMapping("/admin/book/create")
    public String CreateBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("listCategory", categoryService.getAllCategories());
        return "Admin/Book/create";
    }
    @PostMapping("/admin/book/create")
    public String CreateBook(@Valid Book book, BindingResult res, Model model,
                             @RequestParam MultipartFile imageProduct
                             ) throws IOException {
        if(res.hasErrors()) {
            model.addAttribute("book", book);
            model.addAttribute("listCategory", categoryService.getAllCategories());
            return "Admin/Book/create";
        }
        if(imageProduct != null && imageProduct.getSize() > 0)
        {
            try {
                File saveFile = new ClassPathResource("static/img").getFile();
                String newImageFile = UUID.randomUUID() +  ".png";
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + newImageFile);
                Files.copy(imageProduct.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                book.setImage(newImageFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        bookService.addBook(book);
        return "redirect:/admin/books";
    }
    @DeleteMapping("/admin/book/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        bookService.removeBook(id);
        return "redirect:/admin/books";
    }
    @GetMapping("/admin/book/delete/{id}")
    public String getDelete() {
        return "redirect:/";
    }
    @PostMapping("/admin/book/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        var book = bookService.getBook(id);
        model.addAttribute("book", book);
        model.addAttribute("listCategory", categoryService.getAllCategories());
        return "Admin/Book/edit";
    }
    @PostMapping("/admin/book/edit")
    public String edit(Model model,@Valid Book newBook, BindingResult res,
                       @RequestParam MultipartFile imageProduct

                       ) {
        if(res.hasErrors()) {
            model.addAttribute("book", newBook);
            model.addAttribute("listCategory", categoryService.getAllCategories());
            return "Admin/Book/edit";
        }
        if(imageProduct != null && imageProduct.getSize() > 0)
        {
            try {
                File saveFile = new ClassPathResource("static/img").getFile();
                String newImageFile = UUID.randomUUID() +  ".png";
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + newImageFile);
                Files.copy(imageProduct.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                newBook.setImage(newImageFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        bookService.update(newBook);
        return "redirect:/admin/books";
    }
}
