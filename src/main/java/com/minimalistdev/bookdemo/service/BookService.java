package com.minimalistdev.bookdemo.service;

import com.minimalistdev.bookdemo.model.Book;
import com.minimalistdev.bookdemo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    BookRepository repository;

    public ResponseEntity<Page<Book>> filter(Long price, Pageable pageable) {
        return new ResponseEntity<>(repository.findByPriceGreaterThan(price, pageable), HttpStatus.OK);
    }
}
