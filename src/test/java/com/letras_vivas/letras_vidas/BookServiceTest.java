package com.letras_vivas.letras_vidas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.letras_vivas.letras_vidas.bookcrud.model.Book;
import com.letras_vivas.letras_vidas.bookcrud.repository.BookRepository;
import com.letras_vivas.letras_vidas.bookcrud.service.BookService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBooks_shouldReturnList() {
        List<Book> mockBooks = List.of(
                new Book("Book A", "Author A", 2000),
                new Book("Book B", "Author B", 2010));

        when(bookRepository.findAll()).thenReturn(mockBooks);

        List<Book> result = bookService.getAllBooks();

        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getBookById_whenFound_shouldReturnBook() {
        Book book = new Book("Title", "Author", 2020);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.getBookById(1L);

        assertTrue(result.isPresent());
        assertEquals("Title", result.get().getTitle());
    }

    @Test
    void getBookById_whenNotFound_shouldReturnEmptyOptional() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Book> result = bookService.getBookById(99L);

        assertFalse(result.isPresent());
    }

    @Test
    void createBook_shouldSaveAndReturnBook() {
        Book book = new Book("New Book", "Author", 2023);
        when(bookRepository.save(book)).thenReturn(book);

        Book saved = bookService.createBook(book);

        assertEquals("New Book", saved.getTitle());
        verify(bookRepository).save(book);
    }

    @Test
    void updateBook_whenFound_shouldUpdateAndReturn() {
        Book existing = new Book("Old", "Old", 1990);
        Book updated = new Book("New", "New", 2022);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(bookRepository.save(any(Book.class))).thenReturn(existing);

        Book result = bookService.updateBook(1L, updated);

        assertEquals("New", result.getTitle());
        assertEquals(2022, result.getPublicationYear());
    }

    @Test
    void updateBook_whenNotFound_shouldThrow() {
        when(bookRepository.findById(999L)).thenReturn(Optional.empty());

        Book book = new Book("X", "Y", 2000);

        assertThrows(RuntimeException.class, () -> bookService.updateBook(999L, book));
    }

    @Test
    void deleteBook_whenExists_shouldDeleteAndReturnTrue() {
        when(bookRepository.existsById(1L)).thenReturn(true);

        boolean result = bookService.deleteBook(1L);

        assertTrue(result);
        verify(bookRepository).deleteById(1L);
    }

    @Test
    void deleteBook_whenNotExists_shouldReturnFalse() {
        when(bookRepository.existsById(2L)).thenReturn(false);

        boolean result = bookService.deleteBook(2L);

        assertFalse(result);
        verify(bookRepository, never()).deleteById(anyLong());
    }
}
