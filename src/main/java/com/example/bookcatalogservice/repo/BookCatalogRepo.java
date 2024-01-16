package com.example.bookcatalogservice.repo;

import com.example.bookcatalogservice.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookCatalogRepo  extends JpaRepository<Book, Integer> {


    @Query(value = "SELECT b.book_id from book b WHERE b.title = :title", nativeQuery = true)
    List<Integer> findAllByTitle(String title);
}
