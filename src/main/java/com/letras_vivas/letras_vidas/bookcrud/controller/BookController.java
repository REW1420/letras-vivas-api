package com.letras_vivas.letras_vidas.bookcrud.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.letras_vivas.letras_vidas.bookcrud.model.Book;
import com.letras_vivas.letras_vidas.bookcrud.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Books", description = "Book management API")

public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @Operation(summary = "Get all books")
    @ApiResponse(responseCode = "200", description = "Book retrieved successfully", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Book retrieved example", value = """
                         {
                "data": [
                    {
                        "id": 1,
                        "title": "Clean Code",
                        "author": "Robert C. Martin",
                        "publicationYear": 2008
                    }
                ],
                "message": "Books retrieved successfully"
            }
                                    """)))
    @ApiResponse(responseCode = "404", description = "No book found", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "No book found example", value = """
                       {

            }
                        """)))
    public ResponseEntity<Map<String, Object>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        Map<String, Object> response = new HashMap<>();
        if (books.isEmpty()) {

            response.put("data", null);
            response.put("message", "No books found");
            return ResponseEntity.notFound().build();
        }
        response.put("data", books);
        response.put("message", "Books retrieved successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID")
    @ApiResponse(responseCode = "200", description = "Book retrieved successfully", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Book retrieved example", value = """
                                 {
                        "data": [
                            {
                                "id": 1,
                                "title": "Clean Code",
                                "author": "Robert C. Martin",
                                "publicationYear": 2008
                            }
                        ],
            "message": "Book found"
                    }
                                            """)))
    @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Book not found example", value = """
                                     {
                "message": "Book not found"
            }
                                                """)))
    public ResponseEntity<Map<String, Object>> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
                .map(book -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("data", book);
                    response.put("message", "Book found");
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("message", "Book not found");
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                });
    }

    @PostMapping
    @Operation(summary = "Create a new book")
    @ApiResponse(responseCode = "201", description = "Book created successfully", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Book created example", value = """
                       {
                "data": {
                    "id": 1,
                    "title": "Clean Code",
                    "author": "Robert C. Martin",
                    "publicationYear": 2008
                },
                "message": "Book created successfully"
            }
                        """)))

    @ApiResponse(responseCode = "400", description = "Validation failed ", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "A value on body has failed the validation", value = """
                                   {
                "message": "Validation failed",
                "errors": {
                    "author": "Author is required",
                    "publicationYear": "Publication year must be after 1000",
                    "title": "Title is required"
                }
            }
                                    """)))

    public ResponseEntity<Map<String, Object>> createBook(@Valid @RequestBody Book book) {
        Book savedBook = bookService.createBook(book);
        Map<String, Object> response = new HashMap<>();
        response.put("data", savedBook);
        response.put("message", "Book created successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a new book by ID")
    @ApiResponse(responseCode = "404", description = "Book not found ", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Book not found example", value = """
                                   {
                "message": "Validation failed",
                "errors": {
                    "author": "Author is required",
                    "publicationYear": "Publication year must be after 1000",
                    "title": "Title is required"
                }
            }
                                    """)))

    @ApiResponse(responseCode = "400", description = "Validation failed ", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "A value on body has failed the validation", value = """
                                              {
                "message": "Book not found with id 10"
            }
                                                """)))
    @ApiResponse(responseCode = "200", description = "Book updated successfully ", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Book updated successfully example", value = """
                                              {
                "data": {
                    "id": 1,
                    "title": "Clean Code",
                    "author": "Robert C. Martin",
                    "publicationYear": 2008
                },
                "message": "Book updated successfully"
            }
                                                """)))
    public ResponseEntity<Map<String, Object>> updateBook(@PathVariable Long id, @Valid @RequestBody Book book) {
        try {
            Book updated = bookService.updateBook(id, book);
            Map<String, Object> response = new HashMap<>();
            response.put("data", updated);
            response.put("message", "Book updated successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a new book by ID")
    @ApiResponse(responseCode = "404", description = "Book not found ", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Book not found example", value = """
                                              {
                "message": "Book not found"
            }
                                                """)))
    @ApiResponse(responseCode = "200", description = "Book deleted ", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Book deleted example", value = """
                            {
                "message": "Book deleted successfully"
            }
                                                """)))
    public ResponseEntity<Map<String, Object>> deleteBook(@PathVariable Long id) {
        boolean deleted = bookService.deleteBook(id);

        Map<String, Object> response = new HashMap<>();

        if (deleted) {
            response.put("message", "Book deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Book not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

}
