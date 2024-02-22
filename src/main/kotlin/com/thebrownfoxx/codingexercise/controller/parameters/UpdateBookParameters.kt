package com.thebrownfoxx.codingexercise.controller.parameters

data class UpdateBookParameters(
    val bookId: Int,
    val bookName: String,
    val authorName: String
)
