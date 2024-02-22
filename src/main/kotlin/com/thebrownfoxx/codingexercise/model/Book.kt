package com.thebrownfoxx.codingexercise.model

data class Book(
    val id: Int? = null,
    val name: String? = null,
    val author: Author? = null,
) {
    infix fun contentEquals(other: Book): Boolean {
        return name == other.name && author!!.contentEquals(other.author!!)
    }
}