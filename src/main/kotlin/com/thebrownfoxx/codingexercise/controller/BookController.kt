package com.thebrownfoxx.codingexercise.controller

import com.thebrownfoxx.codingexercise.controller.parameters.*
import com.thebrownfoxx.codingexercise.model.Author
import com.thebrownfoxx.codingexercise.model.Book
import com.thebrownfoxx.codingexercise.service.BookService
import lombok.RequiredArgsConstructor
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequiredArgsConstructor
@RestController
class BookController(private val bookService: BookService) {
    @GetMapping("/authors")
    fun getAuthors(): List<Author> {
        return bookService.getAuthors()
    }

    @GetMapping("/authors/byName")
    fun getAuthorsByName(@RequestBody parameters: GetAuthorsByNameParameters): List<Author> {
        val (name) = parameters
        return bookService.getAuthorsByName(name)
    }

    @PatchMapping("/authors")
    fun updateAuthor(@RequestBody parameters: UpdateAuthorParameters) {
        val (id, name) = parameters
        bookService.updateAuthor(id, name)
    }

    @GetMapping("/books")
    fun getBooks(): List<Book> {
        return bookService.getBooks()
    }

    @GetMapping("/books/byName")
    fun getBooksByName(@RequestBody parameters: GetBooksByNameParameters): List<Book> {
        val (name) = parameters
        return bookService.getBooksByName(name)
    }

    @PostMapping("/books")
    fun addBookWithAuthor(@RequestBody parameters: AddBookWithAuthorParameters) {
        val (bookName, authorName) = parameters
        bookService.addBookWithAuthor(bookName, authorName)
    }

    @PatchMapping("/books")
    fun updateBook(@RequestBody parameters: UpdateBookParameters) {
        val (bookId, bookName, authorName) = parameters
        bookService.updateBook(bookId, bookName, authorName)
    }

    @DeleteMapping("/books/{id}")
    fun deleteBook(@PathVariable id: Int) {
        bookService.deleteBook(id)
    }
}