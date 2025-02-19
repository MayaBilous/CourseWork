package com.example.coursework.domain.entity

data class Login(
    val userName: String,
    val password: String,
    val isAdmin: Boolean,
) {

    companion object {
        val default = Login(
            userName = "",
            password = "",
            isAdmin = false,
        )
    }
}
