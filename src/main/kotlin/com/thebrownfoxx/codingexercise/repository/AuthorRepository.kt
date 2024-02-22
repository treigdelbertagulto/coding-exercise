package com.thebrownfoxx.codingexercise.repository

import com.thebrownfoxx.codingexercise.entity.AuthorEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorRepository : JpaRepository<AuthorEntity, Int> {
    fun getByName(name: String): List<AuthorEntity>
}