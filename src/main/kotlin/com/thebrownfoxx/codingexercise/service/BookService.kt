package com.thebrownfoxx.codingexercise.service

import com.thebrownfoxx.codingexercise.entity.AuthorEntity
import com.thebrownfoxx.codingexercise.entity.BookEntity
import com.thebrownfoxx.codingexercise.model.Author
import com.thebrownfoxx.codingexercise.model.Book
import com.thebrownfoxx.codingexercise.repository.AuthorRepository
import com.thebrownfoxx.codingexercise.repository.BookRepository
import com.thebrownfoxx.codingexercise.transformers.toAuthor
import com.thebrownfoxx.codingexercise.transformers.toBook
import org.springframework.stereotype.Service

@Service
class BookService(private val bookRepository: BookRepository, private val authorRepository: AuthorRepository) {
    fun getAuthors(): List<Author> {
        return authorRepository.findAll().map { it.toAuthor() }
    }

    fun getAuthorsByName(name: String): List<Author> {
        return authorRepository.getByName(name).map { it.toAuthor() }
    }

    fun updateAuthor(id: Int, name: String) {
        val newAuthor = authorRepository.getReferenceById(id).copy(name = name)
        authorRepository.save(newAuthor)
    }

    fun getBooks(): List<Book> {
        return bookRepository.findAll().map { it.toBook() }
    }

    fun getBooksByName(name: String): List<Book> {
        return bookRepository.getBooksByName(name).map { it.toBook() }
    }

    fun addBookWithAuthor(bookName: String, authorName: String) {
        val author = authorRepository.getByName(authorName).firstOrNull()
            ?: authorRepository.save(AuthorEntity(name = authorName))
        bookRepository.save(
            BookEntity(
                name = bookName,
                author = author,
            )
        )
    }

    fun updateBook(id: Int, name: String, authorName: String) {
        val author = authorRepository.getByName(authorName).firstOrNull()
            ?: authorRepository.save(AuthorEntity(name = authorName))
        val newBook = BookEntity(
            id = id,
            name = name,
            author = author,
        )
        bookRepository.save(newBook)
    }

    fun deleteBook(id: Int) {
        bookRepository.deleteById(id)
    }
}