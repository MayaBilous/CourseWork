package com.example.coursework.presentation.club

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
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
import com.example.coursework.presentation.club.mvi.ClubViewModel.*
import com.example.coursework.presentation.club.mvi.ClubViewModel
import com.example.coursework.presentation.root.SectionDetailsNavigation
import com.example.coursework.presentation.root.SectionsListNavigation
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubScreen(
    navController: NavController,
    viewModel: ClubViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }
    var dialogText by remember { mutableStateOf("") }

    LaunchedEffect(viewModel.event) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is ClubEvent.NavigateToDetails -> navController.navigate(
                    SectionDetailsNavigation(
                        sectionId = event.sectionId,
                        isAdmin = state.isAdmin,
                        isAddingItem = event.isAddingItem,
                    )
                )

                is ClubEvent.EmptyData -> {
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
                onValueChange = {},
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
                enabled = false,
                label = { Text("name") }
            )
        }

        TextField(
            state.sectionDetails.address,
            onValueChange = { viewModel.process(ClubUserIntent.ChangeAddress(it)) },
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
            state.sectionDetails.workingDays,
            onValueChange = { viewModel.process(ClubUserIntent.ChangeWorkingDays(it)) },
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
            state.sectionDetails.price.toString(),
            onValueChange = { viewModel.process(ClubUserIntent.ChangePrice(it.toInt())) },
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
            label = { Text("price UAH/Occupation") }
        )

        TextField(
            state.sectionDetails.phoneNumber,
            onValueChange = { viewModel.process(ClubUserIntent.ChangePhoneNumber(it)) },
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
                    Button(onClick = { viewModel.process(ClubUserIntent.NavigateToDetails(state.sectionId, false)) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4080f0)
                        )) {
                        Text("Cancel")
                    }
                }else {
                    Column {
                        Text(text = "Add to desired",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp )

                        Checkbox(
                            checked = state.sectionDetails.isSelected,
                            onCheckedChange = {viewModel.process(ClubUserIntent.ChangeSelected)},
                        )
                    }
                }

                Button(onClick = {
                    if (state.isAdmin) {
                        if (state.isAddingItem){
                            viewModel.process(ClubUserIntent.AddClub(state.sectionDetails))
                        }else{
                            viewModel.process(ClubUserIntent.UpdateClub(state.sectionDetails))
                        }
                    }else{
                        viewModel.process(ClubUserIntent.NavigateToDetails(state.sectionId, false))
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
