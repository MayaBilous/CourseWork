package com.example.coursework.presentation.sectionDetails


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.coursework.presentation.ShowDialog
import com.example.coursework.presentation.root.SectionsListNavigation
import com.example.coursework.presentation.sectionDetails.mvi.DetailsSportsSectionsViewModel
import com.example.coursework.presentation.sectionDetails.mvi.DetailsSportsSectionsViewModel.SectionDetailsEvent
import com.example.coursework.presentation.sectionDetails.mvi.DetailsSportsSectionsViewModel.SectionDetailsUserIntent
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformationAboutSportsSections(
    navController: NavController,
    viewModel: DetailsSportsSectionsViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }
    var dialogText by remember { mutableStateOf("") }

    LaunchedEffect(viewModel.event) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is SectionDetailsEvent.Navigate -> navController.navigate(
                    SectionsListNavigation(
                        isAdmin = event.isAdmin
                    )
                )

                is SectionDetailsEvent.EmptyData -> {
                    showDialog = true
                    dialogText = "No data available. Please try again."
                }
            }
        }
    }

    if (showDialog) {
        ShowDialog(text = dialogText, onDismiss = { showDialog = false })
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
                onValueChange = { viewModel.process(SectionDetailsUserIntent.ChangeSectionName(it)) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    disabledTextColor = Color.Black,
                ),
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                enabled = state.isAdmin,
                label = { Text("name") }
            )
        }

        TextField(
            state.sportSection.district,
            onValueChange = { viewModel.process(SectionDetailsUserIntent.ChangeDistrict(it)) },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledTextColor = Color.Black,
            ),
            textStyle = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            ),
            enabled = state.isAdmin,
            label = { Text("district") }
        )

        TextField(
            state.sportSection.address,
            onValueChange = { viewModel.process(SectionDetailsUserIntent.ChangeAddress(it)) },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledTextColor = Color.Black,
            ),
            textStyle = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            ),
            enabled = state.isAdmin,
            label = { Text("address") }
        )

        TextField(
            state.sportSection.workingDays,
            onValueChange = { viewModel.process(SectionDetailsUserIntent.ChangeWorkingDays(it)) },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledTextColor = Color.Black,
            ),
            textStyle = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            ),
            enabled = state.isAdmin,
            label = { Text("working days") }
        )

        TextField(
            state.sportSection.phoneNumber,
            onValueChange = { viewModel.process(SectionDetailsUserIntent.ChangePhoneNumber(it)) },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledTextColor = Color.Black,
            ),
            textStyle = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            ),
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
                    Button(onClick = { viewModel.process(SectionDetailsUserIntent.NavigateToSectionList) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4080f0)
                        )) {
                        Text("Cancel")
                    }
                }
                Button(onClick = {
                    if (state.isAdmin) {
                        if (state.isAddingItem) {
                            viewModel.process(SectionDetailsUserIntent.InsertSportSection(state.sportSection))
                        } else {
                            viewModel.process(SectionDetailsUserIntent.UpdateSportSection(state.sportSection))
                        }
                    }else{
                        viewModel.process(SectionDetailsUserIntent.NavigateToSectionList)
                    }
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4080f0)
                    )) {
                    Text("Ok")
                }
            }
        }
    }

}
