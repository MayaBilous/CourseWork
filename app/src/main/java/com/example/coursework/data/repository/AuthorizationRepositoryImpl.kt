package com.example.coursework.data.repository

import com.example.coursework.data.db.SectionDataBaseProvider
import com.example.coursework.data.repository.mapper.LoginMapper
import com.example.coursework.domain.boundary.AuthorizationRepository
import com.example.coursework.domain.entity.Login

class AuthorizationRepositoryImpl : AuthorizationRepository {

    private val mapper = LoginMapper()
    private val loginDao = SectionDataBaseProvider.db.LoginDao()

    override suspend fun getLogins(): List<Login> {
        return loginDao.getAll().map { mapper.mapToDomain(it) }
    }
}