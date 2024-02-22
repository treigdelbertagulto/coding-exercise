package com.thebrownfoxx.codingexercise.repository

import com.thebrownfoxx.codingexercise.entity.AuthorEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AuthorRepositoryTest {
    @Autowired
    lateinit var bookRepository: BookRepository

    @Autowired
    lateinit var authorRepository: AuthorRepository

    private val sampleData = listOf(
        AuthorEntity(name = "Axwell /\\ Ingrosso"),
        AuthorEntity(name = "Troye Sivan"),
    )

    @BeforeEach
    fun resetDatabase() {
        bookRepository.deleteAll()
        authorRepository.deleteAll()
        authorRepository.saveAll(sampleData)
    }

    @Test
    fun findAll_ShouldReturn_AllAuthors() {
        val repositoryAuthors = authorRepository.findAll()
        assertThat(sampleData).allMatch { sampleAuthor ->
            repositoryAuthors.any { repositoryAuthor ->
                sampleAuthor contentEquals repositoryAuthor
            }
        }
    }

    @Test
    fun findByName_ShouldReturn_Author() {
        val sampleAuthor = sampleData[0]
        val repositoryAuthors = authorRepository.getByName(sampleAuthor.name!!)
        assertThat(sampleAuthor).matches {
            repositoryAuthors.any { repositoryAuthor ->
                it contentEquals repositoryAuthor
            }
        }
    }

    @Test
    fun findByName_ShouldReturn_NoAuthor() {
        val repositoryAuthors = authorRepository.getByName("Nonexistent Author")
        assertThat(repositoryAuthors).isEmpty()
    }

    @Test
    fun addAuthor_ShouldAdd_Author() {
        val newAuthor = AuthorEntity(name = "New Author")
        authorRepository.save(newAuthor)
        val repositoryAuthors = authorRepository.findAll()
        assertThat(repositoryAuthors).contains(newAuthor)
    }

    @Test
    fun updateAuthor_ShouldUpdate_Author() {
        val sampleAuthor = sampleData[0]
        val repositoryAuthors = authorRepository.getByName(sampleAuthor.name!!)
        val updatedAuthor = repositoryAuthors[0].copy(name = "Updated Author")
        authorRepository.save(updatedAuthor)
        val repositoryAuthorsAfterUpdate = authorRepository.getByName(updatedAuthor.name!!)
        assertThat(repositoryAuthorsAfterUpdate).contains(updatedAuthor)
    }
}