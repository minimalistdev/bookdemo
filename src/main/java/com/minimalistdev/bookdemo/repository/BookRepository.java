package com.minimalistdev.bookdemo.repository;

import com.minimalistdev.bookdemo.model.Book;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "book", path = "books")
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    List<Book> findByPriceGreaterThan(Long price);
}