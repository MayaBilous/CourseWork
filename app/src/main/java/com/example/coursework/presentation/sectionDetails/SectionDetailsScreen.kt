package com.example.coursework.presentation.sectionDetails


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
                state.sportSection.sectionName,
                onValueChange = { viewModel.process(SectionsInfoUserIntent.ChangeSectionName(it)) },
                enabled = state.isAdmin,
                label = { Text("name") }
            )
        }

        TextField(
            state.sportSection.district,
            onValueChange = { viewModel.process(SectionsInfoUserIntent.ChangeDistrict(it)) },
            enabled = state.isAdmin,
            label = { Text("district") }
        )

        TextField(
            state.sportSection.address,
            onValueChange = { viewModel.process(SectionsInfoUserIntent.ChangeAddress(it)) },
            enabled = state.isAdmin,
            label = { Text("address") }
        )

        TextField(
            state.sportSection.workingDays,
            onValueChange = { viewModel.process(SectionsInfoUserIntent.ChangeWorkingDays(it)) },
            enabled = state.isAdmin,
            label = { Text("working days") }
        )

        TextField(
            state.sportSection.phoneNumber,
            onValueChange = { viewModel.process(SectionsInfoUserIntent.ChangePhoneNumber(it)) },
            enabled = state.isAdmin,
            label = { Text("phone number") }
        )

        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (state.isAdmin) {
                    Button(onClick = { viewModel.process(SectionsInfoUserIntent.NavigateToSectionList) }) {
                        Text("Cancel")
                    }
                }
                Button(onClick = {
                    if (state.isAdmin) {
                        if (state.isAddingItem) {
                            viewModel.process(SectionsInfoUserIntent.InsertSportSection(state.sportSection))
                        } else {
                            viewModel.process(SectionsInfoUserIntent.UpdateSportSection(state.sportSection))
                        }
                    }
                    viewModel.process(SectionsInfoUserIntent.NavigateToSectionList)
                }) {
                    Text("Ok")
                }
            }
        }
    }

}
