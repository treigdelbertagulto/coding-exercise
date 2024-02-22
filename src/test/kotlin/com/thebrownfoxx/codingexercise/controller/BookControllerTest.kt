package com.thebrownfoxx.codingexercise.controller

import com.thebrownfoxx.codingexercise.controller.parameters.*
import com.thebrownfoxx.codingexercise.entity.AuthorEntity
import com.thebrownfoxx.codingexercise.entity.BookEntity
import com.thebrownfoxx.codingexercise.model.Author
import com.thebrownfoxx.codingexercise.model.Book
import com.thebrownfoxx.codingexercise.repository.AuthorRepository
import com.thebrownfoxx.codingexercise.repository.BookRepository
import org.antlr.v4.runtime.tree.xpath.XPath.findAll
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.jpa.domain.AbstractPersistable_.id

@SpringBootTest
class BookControllerTest {
    @Autowired
    private lateinit var bookController: BookController

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var authorRepository: AuthorRepository

    private val sampleData = listOf(
        Book(
            name = "Sun is Shining",
            author = Author(name = "Axwell /\\ Ingrosso")
        ),
        Book(
            name = "Got Me Started",
            author = Author(name = "Troye Sivan"),
        ),
    )

    @BeforeEach
    fun resetDatabase() {
        // WARNING: TESTING WILL RESET THE DATABASE
        bookRepository.deleteAll()
        authorRepository.deleteAll()

        // Seed data
        val seedAuthors = sampleData.map { bookEntity ->
            AuthorEntity(
                name = bookEntity.author!!.name,
            )
        }
        val savedAuthors = authorRepository.saveAll(seedAuthors)

        val seedBooks = sampleData.mapIndexed { index, bookEntity ->
            BookEntity(
                name = bookEntity.name,
                author = savedAuthors[index],
            )
        }
        bookRepository.saveAll(seedBooks)
    }

    @Test
    fun getAuthors_ShouldReturn_AllAuthors() {
        val controllerAuthors = bookController.getAuthors()
        assertThat(sampleData.map { it.author }).allMatch { sampleAuthor ->
            controllerAuthors.any { controllerAuthor ->
                sampleAuthor?.contentEquals(controllerAuthor) ?: false
            }
        }
        assertThat(sampleData.size).isEqualTo(controllerAuthors.size)
    }

    @Test
    fun getAuthorsByName_ShouldReturn_Author() {
        val name = "Axwell /\\ Ingrosso"
        val controllerAuthors = bookController.getAuthorsByName(GetAuthorsByNameParameters(name))
        assertThat(sampleData.filter { it.author?.name == name }.map { it.author }).anyMatch { sampleAuthor ->
            controllerAuthors.any { controllerAuthor ->
                sampleAuthor?.contentEquals(controllerAuthor) ?: false
            }
        }
    }
    
    @Test
    fun updateAuthor_ShouldUpdate_Author() {
        val author = bookController.getAuthorsByName(GetAuthorsByNameParameters("Axwell /\\ Ingrosso")).first()
        val newName = "Axwell /\\ Ingroceries"
        bookController.updateAuthor(UpdateAuthorParameters(author.id!!, newName))
        val controllerAuthors = bookController.getAuthors()
        assertThat(controllerAuthors.any { it contentEquals Author(author.id, newName) }).isTrue
    }

    @Test
    fun getAll_ShouldReturn_AllBooks() {
        val controllerBooks = bookController.getBooks()
        assertThat(sampleData).allMatch { sampleBook ->
            controllerBooks.any { controllerBook ->
                sampleBook contentEquals controllerBook
            }
        }
        assertThat(sampleData.size).isEqualTo(controllerBooks.size)
    }

    @Test
    fun getBookByName_ShouldReturn_Book() {
        val name = "Sun is Shining"
        val controllerBooks = bookController.getBooksByName(GetBooksByNameParameters(name))
        assertThat(sampleData.filter { it.name == name }).anyMatch { sampleBook ->
            controllerBooks.any { repositoryBook ->
                sampleBook contentEquals repositoryBook
            }
        }
    }

    @Test
    fun addBookWithAuthor_ShouldAdd_Book() {
        val bookName = "Sun is Shining"
        val authorName = "Axwell /\\ Ingrosso"
        bookController.addBookWithAuthor(AddBookWithAuthorParameters(bookName, authorName))
        val controllerBooks = bookController.getBooks()
        assertThat(controllerBooks.any { it.name == bookName }).isTrue
    }

    @Test
    fun updateBook_ShouldUpdate_Book() {
        val book = bookController.getBooksByName(GetBooksByNameParameters("Sun is Shining")).first()
        val newName = "Sun is Shining (Remix)"
        val newAuthor = "Axwell /\\ Ingroceries"
        bookController.updateBook(UpdateBookParameters(book.id!!, newName, newAuthor))
        val controllerBooks = bookController.getBooks()
        assertThat(controllerBooks.any { it contentEquals Book(book.id, newName, Author(name = newAuthor)) }).isTrue
    }

    @Test
    fun deleteBook_ShouldDelete_Book() {
        val bookId = 1
        bookController.deleteBook(bookId)
        val controllerBooks = bookController.getBooks()
        assertThat(controllerBooks.any { it.id == bookId }).isFalse
    }
}