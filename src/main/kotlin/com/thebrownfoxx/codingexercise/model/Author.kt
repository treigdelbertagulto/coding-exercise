package com.thebrownfoxx.codingexercise.model

data class Author(
    val id: Int? = null,
    val name: String? = null,
) {
    infix fun contentEquals(other: Author): Boolean {
        return name == other.name
    }
}