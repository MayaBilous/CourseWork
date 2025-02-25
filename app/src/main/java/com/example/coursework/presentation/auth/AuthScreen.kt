package com.example.coursework.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.coursework.presentation.root.SectionsListNavigation
import com.example.coursework.presentation.auth.mvi.AuthEvent
import com.example.coursework.presentation.auth.mvi.AuthUserIntent
import com.example.coursework.presentation.auth.mvi.AuthViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun Authorization(
    navController: NavController,
    viewModel: AuthViewModel,
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.event) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is AuthEvent.Navigate -> navController.navigate(
                    SectionsListNavigation(
                        isAdmin = event.isAdmin
                    )
                )

                is AuthEvent.EmptyData,
                is AuthEvent.IncorrectData -> {}
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())

    ) {
        TextField(
            value = state.login.userName,
            onValueChange = { viewModel.process(AuthUserIntent.ChangeLogin(it)) },
            label = { Text("Enter login") }
        )

        TextField(
            value = state.login.password,
            onValueChange = { viewModel.process(AuthUserIntent.ChangePassword(it)) },
            label = { Text("Enter password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Button(
            onClick = { viewModel.process(AuthUserIntent.Authorize) }
        ) {
            Text("To authorize")
        }
    }
}