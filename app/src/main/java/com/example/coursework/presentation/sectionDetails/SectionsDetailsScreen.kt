package com.example.coursework.presentation.sectionDetails


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.coursework.presentation.auth.mvi.AuthUserIntent
import com.example.coursework.presentation.root.SectionsListNavigation
import com.example.coursework.presentation.sectionDetails.mvi.InformationAboutSportsSectionsViewModel
import com.example.coursework.presentation.sectionDetails.mvi.InformationAboutSportsSectionsViewModel.SectionsInfoEvent
import com.example.coursework.presentation.sectionDetails.mvi.InformationAboutSportsSectionsViewModel.SectionsInfoUserIntent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun InformationAboutSportsSections(
    navController: NavController,
    viewModel: InformationAboutSportsSectionsViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.event) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is SectionsInfoEvent.Navigate -> navController.navigate(
                    SectionsListNavigation(
                        isAdmin = state.isAdmin
                    )
                )
            }
        }
    }
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)

    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextField(
                value = state.sportSections.name,
                onValueChange = { viewModel.process(SectionsInfoUserIntent.ChangeSectionName(it)) },
                enabled = state.isAdmin,
                label = { Text("name") }
            )
        }

        TextField(
            value = state.sportSections.address,
            onValueChange = { viewModel.process(SectionsInfoUserIntent.ChangeAddress(it)) },
            enabled = state.isAdmin,
            label = { Text("address") }
        )

        TextField(
            value = state.sportSections.workingDays,
            onValueChange = {viewModel.process(SectionsInfoUserIntent.ChangeWorkingDays(it)) },
            enabled = state.isAdmin,
            label = { Text("working days") }
        )

        TextField(
            value = state.sportSections.phoneNumber.toString(),
            onValueChange = {viewModel.process(SectionsInfoUserIntent.ChangePhoneNumber(it.toInt()))  },
            enabled = state.isAdmin,
            label = { Text("phone number") }
        )

        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Button(onClick = { viewModel.process(SectionsInfoUserIntent.NavigateToSectionList) }) {
                Text("Ok")
            }
        }
    }

}
