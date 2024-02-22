package com.thebrownfoxx.codingexercise.transformers

import com.thebrownfoxx.codingexercise.entity.AuthorEntity
import com.thebrownfoxx.codingexercise.model.Author

fun Author.toAuthorEntity() = AuthorEntity(
    id = this.id,
    name = this.name,
)

fun AuthorEntity.toAuthor() = Author(
    id = this.id,
    name = this.name,
)