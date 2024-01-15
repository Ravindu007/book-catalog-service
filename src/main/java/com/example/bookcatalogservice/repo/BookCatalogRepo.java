package com.example.bookcatalogservice.repo;

import com.example.bookcatalogservice.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCatalogRepo  extends JpaRepository<Book, Integer> {


}
