package com.minimalistdev.bookdemo;

import com.minimalistdev.bookdemo.model.Book;
import com.minimalistdev.bookdemo.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void get_book() throws Exception {
        bookRepository.deleteAll();

        mvc.perform(get("/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.page.totalElements").value(0))
                .andExpect(status().isOk());
    }

    @Test
    public void get_one_book() throws Exception {
        bookRepository.deleteAll();

        bookRepository.save(Book.builder().name("Book Name").author("Chris").price(1L).build());

        mvc.perform(get("/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.page.totalElements").value(1))
                .andExpect(status().isOk());
    }

    @Test
    public void get_find_price_greater_than() throws Exception {
        bookRepository.deleteAll();

        bookRepository.save(Book.builder().name("Book Name").author("Chris").price(10L).build());
        bookRepository.save(Book.builder().name("Book Name").author("Chris").price(20L).build());

        mvc.perform(get("/books/filter?price=15")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
