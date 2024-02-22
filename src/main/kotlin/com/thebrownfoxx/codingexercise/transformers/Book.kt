package com.thebrownfoxx.codingexercise.transformers

import com.thebrownfoxx.codingexercise.entity.BookEntity
import com.thebrownfoxx.codingexercise.model.Book

fun Book.toBookEntity() = BookEntity(
    id = this.id,
    name = this.name,
    author = this.author?.toAuthorEntity()
)

fun BookEntity.toBook() = Book(
    id = this.id,
    name = this.name,
    author = this.author?.toAuthor()
)