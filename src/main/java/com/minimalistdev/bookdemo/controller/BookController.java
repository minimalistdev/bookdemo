package com.minimalistdev.bookdemo.controller;

import com.minimalistdev.bookdemo.config.DefaultExceptionHandler;
import com.minimalistdev.bookdemo.model.Book;
import com.minimalistdev.bookdemo.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

    private Logger log = LoggerFactory.getLogger(BookController.class);

    @Autowired
    BookService service;

    @GetMapping(value = "/books/filter")
    public ResponseEntity<Page<Book>> bookFilter(@RequestParam(value = "price[gt]") Long price, Pageable pageable) {
        log.info("books request received with filter");
        return service.filter(price, pageable);
    }
}
