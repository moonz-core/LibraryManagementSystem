
package com.example.library.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.library.model.Book;

@RestController
@RequestMapping("/books")
public class BookController {

    // ArrayList to store book records
    private List<Book> books = new ArrayList<>();

    // POST - Add a new book
    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody Book book) {

        // Check for duplicate ID
        for (Book b : books) {
            if (b.getId().equals(book.getId())) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("Book ID already exists!");
            }
        }

        books.add(book);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(book);
    }

    // GET - Retrieve all books
    @GetMapping
    public List<Book> getAllBooks() {

        return books;
    }

    // GET - Retrieve book by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(
            @PathVariable Integer id) {

        for (Book b : books) {

            if (b.getId().equals(id)) {
                return ResponseEntity.ok(b);
            }
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Book not found!");
    }

    // PUT - Update book details
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(
            @PathVariable Integer id,
            @RequestBody Book updatedBook) {

        for (Book b : books) {

            if (b.getId().equals(id)) {

                b.setTitle(updatedBook.getTitle());
                b.setAuthor(updatedBook.getAuthor());
                b.setCategory(updatedBook.getCategory());
                b.setAvailableCopies(
                        updatedBook.getAvailableCopies());

                return ResponseEntity.ok(b);
            }
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Book not found!");
    }

    // DELETE - Delete book by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(
            @PathVariable Integer id) {

        for (Book b : books) {

            if (b.getId().equals(id)) {

                books.remove(b);

                return ResponseEntity.ok(
                        "Book deleted successfully!");
            }
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Book not found!");
    }
}
