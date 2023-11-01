package com.example.ecommerce.service;

import com.example.ecommerce.model.Book;
import com.example.ecommerce.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepo bookRepo;
    public List<Book> getAllBook() {
        return bookRepo.findAll();
    }
    public Book getBook(Long id) {
        return bookRepo.findById(id).orElse(null);
    }
    public void addBook(Book book) {
        bookRepo.save(book);
    }
    public void removeBook(Long id) {
        bookRepo.deleteById(id);
    }
    public void update(Book newBook) {
        bookRepo.save(newBook);
    }
    public Page<Book> paginateCourse(Long category, Pageable page) {
        Specification<Book> spec = Specification.where(null);
        if (category != 0) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("category").get("id"), category)
            );
        }
        return bookRepo.findAll(spec,page);
    }
}
