package com.example.ecommerce.repo;

import com.example.ecommerce.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepo extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
}
