package com.example.bookservice.controller;

import com.example.bookservice.pojos.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    @GetMapping("{bookId}")
    public Book getBook(@PathVariable String bookId) {
        return new Book(bookId,"test name","test desc");
    }
}
