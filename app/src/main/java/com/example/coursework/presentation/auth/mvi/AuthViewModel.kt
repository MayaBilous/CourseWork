package com.example.coursework.presentation.auth.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursework.domain.entity.AuthResult
import com.example.coursework.domain.entity.Login
import com.example.coursework.domain.usecase.CheckAuthorization
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AuthViewModel(
    private val checkAuthorization: CheckAuthorization,
) : ViewModel() {

    private var _state = MutableStateFlow(AuthState(login = Login.default))
    val state: StateFlow<AuthState>
        get() = _state.asStateFlow()

    private val _userIntent = MutableSharedFlow<AuthUserIntent>()

    private val _event = MutableSharedFlow<AuthEvent>()
    val event: SharedFlow<AuthEvent>
        get() = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            _userIntent.collectLatest { intent ->
                when (intent) {
                    is AuthUserIntent.ChangeLogin -> changeLogin(userName = intent.userName)
                    is AuthUserIntent.ChangePassword -> changePassword(password = intent.password)
                    is AuthUserIntent.Authorize -> checkAuthorization(state = state.value)
                }
            }
        }
    }

    private suspend fun changeLogin(userName: String) {
        _state.emit(
            state.value.copy(
                login = _state.value.login.copy(userName = userName)
            )
        )
    }

    private suspend fun changePassword(password: String) {
        _state.emit(
            state.value.copy(
                login = _state.value.login.copy(password = password)
            )
        )
    }

    private suspend fun checkAuthorization(state: AuthState) {
        val result = checkAuthorization(login = state.login)
        when (result) {
            AuthResult.EMPTY -> _event.emit(AuthEvent.EmptyData)
            AuthResult.INCORRECT -> _event.emit(AuthEvent.IncorrectData)
            AuthResult.USER -> _event.emit(AuthEvent.Navigate(isAdmin = false))
            AuthResult.ADMIN -> _event.emit(AuthEvent.Navigate(isAdmin = true))
        }
    }

    fun process(userIntent: AuthUserIntent) {
        viewModelScope.launch {
            _userIntent.emit(userIntent)
        }
    }
}


data class AuthState(
    val login: Login
)

sealed interface AuthUserIntent {
    data class ChangeLogin(val userName: String) : AuthUserIntent
    data class ChangePassword(val password: String) : AuthUserIntent
    data object Authorize : AuthUserIntent
}

sealed interface AuthEvent {
    data class Navigate(val isAdmin: Boolean) : AuthEvent
    data object EmptyData : AuthEvent
    data object IncorrectData : AuthEvent
}