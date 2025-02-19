package com.example.coursework.domain.boundary

import com.example.coursework.domain.entity.Login

interface AuthorizationRepository {

    suspend fun getLogins(): List<Login>
}