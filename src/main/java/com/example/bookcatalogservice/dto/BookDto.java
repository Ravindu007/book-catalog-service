package com.example.bookcatalogservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDto {

    private Integer bookId; //primary key
    private String title;
    private String author;
    private double price;
    private String isbn;
}
