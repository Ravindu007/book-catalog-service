package com.example.bookcatalogservice.service;


import com.example.bookcatalogservice.dto.BookDto;
import com.example.bookcatalogservice.entity.Book;
import com.example.bookcatalogservice.feign.InventoryInterface;
import com.example.bookcatalogservice.repo.BookCatalogRepo;
import com.example.bookcatalogservice.util.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class BookCatalogService {


    @Autowired
    private BookCatalogRepo bookCatalogRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InventoryInterface inventoryInterface;


    //create a book in the database
    public String saveBook(BookDto bookDto) {
        if(bookCatalogRepo.existsById(bookDto.getBookId())){
            return VarList.RSP_DUPLICATED;
        }else{
            //create a record
            bookCatalogRepo.save(modelMapper.map(bookDto, Book.class));

            //update the inventory
            //have to call the /updateTheStockOfParticularTitle/{title} in inventory service
            scheduleInventoryUpdate(bookDto.getTitle());
            return VarList.RSP_SUCCESS;
        }
    }

    private void scheduleInventoryUpdate(String title) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        // Define the task to update inventory
        Runnable inventoryUpdateTask = () -> {
            // Your inventory update logic goes here
            inventoryInterface.updateListOfBooksForTitle(title);
            System.out.println("Inventory updated for title: " + title);
        };

        // Schedule the inventory update task after a delay of 1 second
        long delayInSeconds = 1;
        executorService.schedule(inventoryUpdateTask, delayInSeconds, TimeUnit.SECONDS);

        // Shutdown the executor service when no longer needed
        executorService.shutdown();
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


    //get a list of books of a particular title
    public List<Integer> getAllbooksofParticlarTitle(String title) {

        List<Integer> bookList = bookCatalogRepo.findAllByTitle(title);
        return bookList;
    }
}
