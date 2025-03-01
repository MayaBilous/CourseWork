package com.example.coursework.data.repository.mapper

import com.example.coursework.data.db.model.DbLogin
import com.example.coursework.domain.entity.Login

class LoginMapper {
    fun mapToDomain(dbLogin: DbLogin): Login {
        return Login(
            userName = dbLogin.userName,
            password = dbLogin.password,
            isAdmin = dbLogin.isAdmin,
        )
    }
}