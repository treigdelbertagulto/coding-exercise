package com.thebrownfoxx.codingexercise.repository

import com.thebrownfoxx.codingexercise.entity.BookEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : JpaRepository<BookEntity, Int> {
    fun getBooksByName(name: String): List<BookEntity>
}