package com.minimalistdev.bookdemo.controller;

import com.minimalistdev.bookdemo.model.Book;
import com.minimalistdev.bookdemo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookService service;

    @GetMapping(value = "/books/filter")
    public ResponseEntity<List<Book>> bookFilter(@RequestParam Long price) {
        return service.filter(price);
    }
}
