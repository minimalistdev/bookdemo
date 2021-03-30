package com.minimalistdev.bookdemo.service;

import com.minimalistdev.bookdemo.model.Book;
import com.minimalistdev.bookdemo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository repository;

    public ResponseEntity<List<Book>> filter(Long price) {
        return new ResponseEntity<>(repository.findByPriceGreaterThan(price), HttpStatus.OK);
    }
}
