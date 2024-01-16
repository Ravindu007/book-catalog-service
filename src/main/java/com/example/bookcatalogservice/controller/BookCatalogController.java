package com.example.bookcatalogservice.controller;

import com.example.bookcatalogservice.dto.BookDto;
import com.example.bookcatalogservice.dto.ResponseDto;
import com.example.bookcatalogservice.service.BookCatalogService;
import com.example.bookcatalogservice.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
