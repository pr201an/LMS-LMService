package com.example.lmservice.controller;

import com.example.lmservice.model.Book;
import com.example.lmservice.service.impl.BookServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();
    @InjectMocks
    private BookController bookController;
    @Mock
    private BookServiceImpl bookService;

    @BeforeEach
    public void setUp() throws Exception{
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void shouldCreateMockMvc() {
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    public void shouldReturnListOfBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(List.of(
                new Book("1L","Zero To One","Present")));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/books").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bookName").value("Zero To One"));
    }

    @Test
    public void shouldCreateBook() throws Exception {
        //ARRANGE - given some preconditions
        Book book = new Book("1L", "Zero To One", "Present");
        Mockito.when(bookService.addBook(Mockito.any(Book.class))).thenReturn(book);

        //ACT - when an action occurs
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(book)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        //ASSERT - verify the output
        String request = result.getResponse().getContentAsString();
        Book book1 = mapper.readValue(request, Book.class);
        Assertions.assertEquals(book1.bookName, "Zero To One");
    }

    @Test
    public void borrowBookTest() throws Exception {
        Book book = new Book("1L", "Zero To One", "Absent");
        Mockito.when(bookService.updateBook(Mockito.any(Book.class))).thenReturn(book);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/book/Zero To One")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(book)))
                .andReturn();
        String request = result.getResponse().getContentAsString();
        Book book1 = mapper.readValue(request, Book.class);
        Assertions.assertEquals(book1.bookStatus, "Absent");
    }

    @Test
    public void borrowBook() throws Exception {
        Book book = new Book("1L", "Zero To One", "Present");
        Mockito.when(bookService.getBookByName("Zero To One")).thenReturn(book);
        //Mockito.when(bookService.updateBook(Mockito.any(Book.class))).thenReturn(book);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/book/borrow/Zero To One")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(book)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String request = result.getResponse().getContentAsString();
        Assertions.assertEquals(request, "Returning from MongoDB, Book Borrowed!");
    }
}
