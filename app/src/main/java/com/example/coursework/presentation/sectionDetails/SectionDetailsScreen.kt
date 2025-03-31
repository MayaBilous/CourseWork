package com.example.coursework.presentation.sectionDetails


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import com.example.coursework.presentation.root.ClubNavigation
import com.example.coursework.presentation.root.SectionsListNavigation
import com.example.coursework.presentation.sectionDetails.mvi.DetailsSportsSectionsViewModel
import com.example.coursework.presentation.sectionDetails.mvi.DetailsSportsSectionsViewModel.SectionDetailsEvent
import com.example.coursework.presentation.sectionDetails.mvi.DetailsSportsSectionsViewModel.SectionDetailsUserIntent
import com.example.coursework.presentation.sectionList.mvi.SectionListViewModel.SectionListUserIntent.DeleteSportSectionWithDetails
import com.example.coursework.presentation.sectionList.mvi.SectionListViewModel.SectionListUserIntent.NavigateToSectionDetails
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SectionDetailsScreen(
    navController: NavController,
    viewModel: DetailsSportsSectionsViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }
    var dialogText by remember { mutableStateOf("") }

    LaunchedEffect(viewModel.event) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is SectionDetailsEvent.NavigateToSectionList -> navController.navigate(
                    SectionsListNavigation(
                        isAdmin = event.isAdmin
                    )
                )

                is SectionDetailsEvent.EmptyData -> {
                    showDialog = true
                    dialogText = "No data available. Please try again."
                }

                is SectionDetailsEvent.NavigateToClub -> navController.navigate(
                    ClubNavigation(
                        sectionId = event.sectionId,
                        isAdmin = state.isAdmin,
                        isAddingItem = event.isAddingItem,
                        detailsId = event.detailsId,
                    )
                )
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

        if (!state.isAddingItem){
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(modifier = Modifier.align(Alignment.Bottom),
                    text = "Clubs: ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp )

                if (state.isAdmin) {
                    Button (onClick = { viewModel.process(SectionDetailsUserIntent.NavigateToClub(
                        sectionId = state.sectionId ?:0,
                        isAddingItem = true,
                        detailsId = null,
                    ))},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4080f0)
                        )) {
                        Text("Add")
                    }
                }
            }
    }


        Column(
            modifier = Modifier
        ) {
            state.sportSection.sectionDetails.forEach { item ->
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(text = item.address,
                        modifier = Modifier
                            .clickable {
                                viewModel.process(SectionDetailsUserIntent.NavigateToClub(
                                    sectionId = item.sectionId ?:0,
                                    isAddingItem = false,
                                    detailsId = item.detailsId,
                                ))
                            })

                    if (state.isAdmin) {
                        Icon(
                            Icons.Default.Delete, "",
                            modifier = Modifier
                                .clickable {
                                    viewModel.process(
                                        SectionDetailsUserIntent.DeleteSectionDetails(item.detailsId ?:0)
                                    )
                                })
                    }
                }
            }
        }

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
                        if (state.isAddingItem){
                            viewModel.process(SectionDetailsUserIntent.AddSportSection(state.sportSection))
                        }else{
                            viewModel.process(SectionDetailsUserIntent.UpdateSection(state.sportSection))
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
