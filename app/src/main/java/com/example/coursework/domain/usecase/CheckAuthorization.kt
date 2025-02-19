package com.example.coursework.domain.usecase

import com.example.coursework.domain.boundary.AuthorizationRepository
import com.example.coursework.domain.entity.AuthResult
import com.example.coursework.domain.entity.Login

interface CheckAuthorization {

    suspend operator fun invoke(login: Login): AuthResult
}

class CheckAuthorizationUseCase(
    private val authorizationRepository: AuthorizationRepository,
) : CheckAuthorization {

    override suspend fun invoke(login: Login): AuthResult {
        if (login.userName.isEmpty() || login.password.isEmpty()) {
            return AuthResult.EMPTY
        }

        return authorizationRepository.getLogins().find {
            it.userName == login.userName &&
                    it.password == login.password
        }?.let {
            if (it.isAdmin) AuthResult.ADMIN else AuthResult.USER
        } ?: AuthResult.INCORRECT
    }
}
