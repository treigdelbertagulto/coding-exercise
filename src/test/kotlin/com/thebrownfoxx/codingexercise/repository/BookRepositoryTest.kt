package com.thebrownfoxx.codingexercise.repository

import com.thebrownfoxx.codingexercise.entity.AuthorEntity
import com.thebrownfoxx.codingexercise.entity.BookEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BookRepositoryTest {
    @Autowired
    lateinit var bookRepository: BookRepository

    @Autowired
    lateinit var authorRepository: AuthorRepository

    private val sampleData = listOf(
        BookEntity(
            name = "Sun is Shining",
            author = AuthorEntity(name = "Axwell /\\ Ingrosso")
        ),
        BookEntity(
            name = "Got Me Started",
            author = AuthorEntity(name = "Troye Sivan"),
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
    fun findAll_ShouldReturn_AllBooks() {
        val repositoryBooks = bookRepository.findAll()
        assertThat(sampleData).allMatch { sampleBook ->
            repositoryBooks.any { repositoryBook ->
                sampleBook contentEquals repositoryBook
            }
        }
        assertThat(sampleData.size).isEqualTo(repositoryBooks.size)
    }

    @Test
    fun getBookByName_ShouldReturn_Book() {
        val sampleBook = sampleData[0]
        val repositoryBooks = bookRepository.getBooksByName(sampleBook.name!!)
        assertThat(sampleBook).matches {
            repositoryBooks.any { repositoryBook ->
                it contentEquals repositoryBook
            }
        }
    }

    @Test
    fun getBooksByName_ShouldReturn_NoBooks() {
        val repositoryBooks = bookRepository.getBooksByName("Nonexistent Book")
        assertThat(repositoryBooks).isEmpty()
    }

    @Test
    fun addBook_ShouldAdd_Book() {
        val newAuthor = AuthorEntity(name = "New Author")
        val savedAuthor = authorRepository.save(newAuthor)
        val newBook = BookEntity(
            name = "New Book",
            author = savedAuthor,
        )
        val savedBook = bookRepository.save(newBook)
        val repositoryBooks = bookRepository.findAll()
        assertThat(repositoryBooks).anyMatch { it contentEquals savedBook }
    }

    @Test
    fun updateBook_ShouldUpdate_Book() {
        val bookToUpdate = sampleData[0]
        val newAuthor = AuthorEntity(name = "New Author")
        val savedAuthor = authorRepository.save(newAuthor)
        val updatedBook = bookToUpdate.copy(
            name = "Updated Book",
            author = savedAuthor,
        )
        bookRepository.save(updatedBook)
        val repositoryBooks = bookRepository.findAll()
        assertThat(repositoryBooks).anyMatch { it contentEquals updatedBook }
    }

    @Test
    fun deleteBook_ShouldDelete_Book() {
        val bookToDelete = bookRepository.findAll().first()
        bookRepository.delete(bookToDelete)
        val repositoryBooks = bookRepository.findAll()
        assertThat(repositoryBooks).noneMatch { it contentEquals bookToDelete }
    }
}