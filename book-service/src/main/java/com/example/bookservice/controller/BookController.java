package com.example.bookservice.controller;

import com.example.bookservice.pojos.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final Environment environment;


    @GetMapping("{bookId}")
    public Book getBook(@PathVariable String bookId) {
        String port = environment.getProperty("local.server.port");
        return new Book(bookId,port,"test desc");
    }
}
