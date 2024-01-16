package com.example.bookcatalogservice.service;


import com.example.bookcatalogservice.dto.BookDto;
import com.example.bookcatalogservice.entity.Book;
import com.example.bookcatalogservice.repo.BookCatalogRepo;
import com.example.bookcatalogservice.util.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BookCatalogService {


    @Autowired
    private BookCatalogRepo bookCatalogRepo;

    @Autowired
    private ModelMapper modelMapper;

    public String saveBook(BookDto bookDto) {
        if(bookCatalogRepo.existsById(bookDto.getBookId())){
            return VarList.RSP_DUPLICATED;
        }else{
            bookCatalogRepo.save(modelMapper.map(bookDto, Book.class));
            return VarList.RSP_SUCCESS;
        }
    }

    public List<BookDto> getAllBooks() {
        List<Book> allBooksFromDb = bookCatalogRepo.findAll();
        return modelMapper.map(allBooksFromDb, new TypeToken<ArrayList<BookDto>>(){}.getType());
    }


    //create a book in the database

    //view a book in the database

    //update a book in the database

    //delete a book in the database
}
