package com.example.bookcatalogservice.controller;

import com.example.bookcatalogservice.dto.BookDto;
import com.example.bookcatalogservice.dto.ResponseDto;
import com.example.bookcatalogservice.entity.Book;
import com.example.bookcatalogservice.service.BookCatalogService;
import com.example.bookcatalogservice.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/bookCatalog")
public class BookCatalogController {

    //Getting service here
    @Autowired
    private BookCatalogService bookCatalogService;

    @Autowired
    private ResponseDto responseDto;


    //create a book in the database
    @PostMapping(value = "/create")  //api/v1/bookCatalog/create
    public ResponseEntity createSingleBook(@RequestBody BookDto bookDto){
        try{
            //save the book in the database
            String res = bookCatalogService.saveBook(bookDto);


            // handle the response
            if(res.equals("00")){
                responseDto.setCode(VarList.RSP_SUCCESS);
                responseDto.setMessage("successfully saved");
                responseDto.setContent(bookDto);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else{
                responseDto.setCode(VarList.RSP_ERROR);
                responseDto.setMessage("error");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }

        }catch (Exception ex){
            //exception handling
            responseDto.setCode(VarList.RSP_ERROR);
            responseDto.setMessage(ex.getMessage());
            responseDto.setContent(null);
            return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
        }
    }


    //get all books
    @GetMapping(value = "/getAllBooks") //api/v1/bookCatalog/getAllBooks
    public ResponseEntity getAllBooks(){
        try{
            List<BookDto> books = bookCatalogService.getAllBooks();

            responseDto.setCode(VarList.RSP_SUCCESS);
            responseDto.setMessage("success");
            responseDto.setContent(books);
            return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
        }catch (Exception ex){
            responseDto.setCode(VarList.RSP_ERROR);
            responseDto.setMessage(ex.getMessage());
            responseDto.setContent(null);
            return new ResponseEntity(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    //update a book
    @PutMapping(value = "/updateBook") //api/v1/bookCatalog/updateBook
    public ResponseEntity updateABookById(@RequestBody BookDto updatedBook){
        try{
            String res = bookCatalogService.updateBookById(updatedBook);

            //handling responses
            if(res.equals("00")){
                responseDto.setCode("00");
                responseDto.setMessage("updated successfully");
                responseDto.setContent(updatedBook);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else{
                responseDto.setCode("05");
                responseDto.setMessage("error");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.BAD_REQUEST);
            }
        }catch(Exception ex){
            responseDto.setCode("05");
            responseDto.setMessage(ex.getMessage());
            responseDto.setContent(null);
            return new ResponseEntity(responseDto, HttpStatus.BAD_REQUEST);
        }
    }


    //delete book by id
    @DeleteMapping(value="/deleteBook/{bookId}")
    public ResponseEntity deleteBookByID(@PathVariable Integer bookId){
        try{
            String res = bookCatalogService.deleteBookById(bookId);

            if(res.equals("00")){
                responseDto.setCode("00");
                responseDto.setMessage("successfully deleted");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else{
                responseDto.setCode("05");
                responseDto.setMessage("error");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            responseDto.setCode("05");
            responseDto.setMessage(ex.getMessage());
            responseDto.setContent(null);
            return new ResponseEntity(responseDto, HttpStatus.BAD_REQUEST);
        }
    }



    //get a collection of bookIds of a particular title and send them to stock service
    @GetMapping(value = "/getBookListofTitle/{title}")
    public  ResponseEntity<List<Integer>> getaBookListbyTitle(@PathVariable String title){
        try{
            //return a set of Ids
            List<Integer> books = bookCatalogService.getAllbooksofParticlarTitle(title);
            return new ResponseEntity<>(books,HttpStatus.OK);

        }catch (Exception ex){
            responseDto.setCode("05");
            responseDto.setMessage("error");
            responseDto.setContent(null);
            return new ResponseEntity(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

}

