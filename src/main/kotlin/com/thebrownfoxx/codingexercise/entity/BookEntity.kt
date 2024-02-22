package com.thebrownfoxx.codingexercise.entity

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
data class BookEntity(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id val id: Int? = null,
    @Column val name: String? = null,
    @OneToOne val author: AuthorEntity? = null,
) {
    infix fun contentEquals(other: BookEntity): Boolean {
        return this.name == other.name && this.author?.contentEquals(other.author!!)!!
    }
}