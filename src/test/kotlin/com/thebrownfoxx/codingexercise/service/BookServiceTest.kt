package com.thebrownfoxx.codingexercise.service

import com.thebrownfoxx.codingexercise.entity.AuthorEntity
import com.thebrownfoxx.codingexercise.entity.BookEntity
import com.thebrownfoxx.codingexercise.model.Author
import com.thebrownfoxx.codingexercise.model.Book
import com.thebrownfoxx.codingexercise.repository.AuthorRepository
import com.thebrownfoxx.codingexercise.repository.BookRepository
import com.thebrownfoxx.codingexercise.transformers.toAuthor
import com.thebrownfoxx.codingexercise.transformers.toBook
import com.thebrownfoxx.codingexercise.transformers.toBookEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.data.jpa.domain.AbstractPersistable_.id

class BookServiceTest {
    private val bookRepository: BookRepository = mock(BookRepository::class.java)
    private val authorRepository: AuthorRepository = mock(AuthorRepository::class.java)
    private val bookService: BookService = BookService(bookRepository, authorRepository)

    private val sampleData = listOf(
        BookEntity(
            id = 1,
            name = "Sun is Shining",
            author = AuthorEntity(id = 1, name = "Axwell /\\ Ingrosso")
        ),
        BookEntity(
            id = 2,
            name = "Got Me Started",
            author = AuthorEntity(id = 2, name = "Troye Sivan"),
        ),
    )

    @Test
    fun getAuthors_ShouldReturn_AllAuthors() {
        `when`(authorRepository.findAll()).thenReturn(
            sampleData.map { it.author!! }
        )
        val authors = bookService.getAuthors()
        assertThat(authors).containsExactlyInAnyOrder(
            *sampleData.map { Author(it.author!!.id, it.author!!.name) }.toTypedArray()
        )
    }

    @Test
    fun getAuthorsByName_ShouldReturn_AllAuthorsByName() {
        val name = "Axwell /\\ Ingrosso"
        `when`(authorRepository.getByName(name)).thenReturn(
            sampleData.map { it.author!! }.filter { it.name == name }
        )
        val authors = bookService.getAuthorsByName(name)
        assertThat(authors).containsExactlyInAnyOrder(
            *sampleData.map { it.author!!.toAuthor() }.filter { it.name == name }.toTypedArray()
        )
    }

    @Test
    fun getBooks_ShouldReturn_AllBooks() {
        `when`(bookRepository.findAll()).thenReturn(sampleData)
        val books = bookService.getBooks()
        assertThat(books).containsExactlyInAnyOrder(
            *sampleData.map { it.toBook() }.toTypedArray()
        )
    }

    @Test
    fun getBooksByName_ShouldReturn_AllBooksByName() {
        val name = "Sun is Shining"
        `when`(bookRepository.getBooksByName(name)).thenReturn(
            sampleData.filter { it.name == name }
        )
        val books = bookService.getBooksByName(name)
        assertThat(books).containsExactlyInAnyOrder(
            *sampleData.filter { it.name == name }.map { it.toBook() }.toTypedArray()
        )
    }
}