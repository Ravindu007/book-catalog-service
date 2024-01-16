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


    //create a book in the database
    public String saveBook(BookDto bookDto) {
        if(bookCatalogRepo.existsById(bookDto.getBookId())){
            return VarList.RSP_DUPLICATED;
        }else{
            bookCatalogRepo.save(modelMapper.map(bookDto, Book.class));
            return VarList.RSP_SUCCESS;
        }
    }


    //get all books in the database
    public List<BookDto> getAllBooks() {
        List<Book> allBooksFromDb = bookCatalogRepo.findAll();
        return modelMapper.map(allBooksFromDb, new TypeToken<ArrayList<BookDto>>(){}.getType());
    }

    //update an existing book
    public String updateBookById(BookDto updatedBook) {
        if(bookCatalogRepo.existsById(updatedBook.getBookId())){
            bookCatalogRepo.save(modelMapper.map(updatedBook, Book.class));
            return VarList.RSP_SUCCESS;
        }else{
            return VarList.RSP_ERROR;
        }
    }

    public String deleteBookById(Integer bookId) {
        if(bookCatalogRepo.existsById(bookId)){
            bookCatalogRepo.deleteById(bookId);
            return VarList.RSP_SUCCESS;
        }else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }
}
