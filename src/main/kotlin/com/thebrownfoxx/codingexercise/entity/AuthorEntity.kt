package com.thebrownfoxx.codingexercise.entity

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Entity
@Table(name = "author")
@Data
@NoArgsConstructor
@AllArgsConstructor
data class AuthorEntity(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id val id: Int? = null,
    @Column val name: String? = null,
) {
    infix fun contentEquals(other: AuthorEntity): Boolean {
        return this.name == other.name
    }
}