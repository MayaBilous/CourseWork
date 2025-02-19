package com.example.coursework.data.repository

import com.example.coursework.domain.boundary.AuthorizationRepository
import com.example.coursework.domain.entity.Login

class AuthorizationRepositoryImpl: AuthorizationRepository {

    override suspend fun getLogins(): List<Login> {
        return listOf(
            Login(userName = "admin", password = "123", isAdmin = true),
            Login(userName = "user", password = "123", isAdmin = false),
        )
    }
}