package com.minimalistdev.bookdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minimalistdev.bookdemo.model.Book;
import com.minimalistdev.bookdemo.repository.BookRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookRepository bookRepository;

    @Before
    public void before() {
        bookRepository.deleteAll();
    }

    @Test
    public void get_book() throws Exception {
        mvc.perform(get("/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.page.totalElements").value(0))
                .andExpect(status().isOk());
    }

    @Test
    public void get_one_book() throws Exception {
        bookRepository.save(Book.builder().name("Book Name").author("Chris").price(1L).build());

        mvc.perform(get("/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.page.totalElements").value(1))
                .andExpect(status().isOk());
    }

    @Test
    public void get_find_price_greater_than() throws Exception {
        bookRepository.save(Book.builder().name("Book Name").author("Chris").price(10L).build());
        bookRepository.save(Book.builder().name("Book Name").author("Chris").price(20L).build());

        mvc.perform(get("/books/filter?price[gt]=15")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.numberOfElements", is(1)))
                .andExpect(status().isOk());
    }

    @Test
    public void crate_a_book() throws Exception {
        Book book = Book.builder().price(1L).name("Book name").author("Author").build();

        mvc.perform(post("/books")
                .content(toJson(book))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void update_a_book() throws Exception {
        Book savedBook = bookRepository.save(Book.builder().name("Book Name").author("Chris").price(10L).build());
        Book updatedData = Book.builder().name("New Book").author("Jana").price(20L).build();

        mvc.perform(put("/books/"+savedBook.getId())
                .content(toJson(updatedData))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Book updateBook = bookRepository.findById(savedBook.getId()).get();
        Assert.assertEquals("New Book", updateBook.getName());
        Assert.assertEquals("Jana", updateBook.getAuthor());
        Assert.assertEquals(Long.valueOf(20), updateBook.getPrice());
    }

    public String toJson(Object ob) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(ob);
    }
}
