package com.example.coursework.presentation.sectionList

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.coursework.R
import com.example.coursework.presentation.root.AuthNavigation
import com.example.coursework.presentation.root.SectionDetailsNavigation
import com.example.coursework.presentation.sectionList.mvi.SectionListViewModel
import com.example.coursework.presentation.sectionList.mvi.SectionListViewModel.SectionListEvent
import com.example.coursework.presentation.sectionList.mvi.SectionListViewModel.SectionListUserIntent.DeleteSportSectionWithDetails
import com.example.coursework.presentation.sectionList.mvi.SectionListViewModel.SectionListUserIntent.InputSearchText
import com.example.coursework.presentation.sectionList.mvi.SectionListViewModel.SectionListUserIntent.NavigateToAuth
import com.example.coursework.presentation.sectionList.mvi.SectionListViewModel.SectionListUserIntent.NavigateToSectionDetails
import com.example.coursework.presentation.sectionList.mvi.SectionListViewModel.SectionListUserIntent.Sorting
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SectionListScreen(
    navController: NavController,
    viewModel: SectionListViewModel
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.event) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is SectionListEvent.NavigateToSD -> navController.navigate(
                    SectionDetailsNavigation(
                        sectionId = event.sportSectionId,
                        isAdmin = state.isAdmin,
                        isAddingItem = event.isAddingItem,
                    )
                )

                is SectionListEvent.NavigateToA -> navController.navigate(
                    AuthNavigation
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
            Image(
                painter = painterResource(id = R.drawable.sport),
                contentDescription = "sport",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {

            TextField(modifier = Modifier
                .width(90.dp)
                .height(25.dp),
                value = state.searchText,
                onValueChange = { viewModel.process(InputSearchText(it)) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                ),
                label = { Text("Search", fontSize = 12.sp) })

            Button(
                onClick = { viewModel.process(Sorting) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4080f0)
                )
            ) {
                Text(state.sortButtonText)
            }

        }

        Column(
            modifier = Modifier
        ) {
            state.uiSportSection.forEach { item ->
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(text = item.sectionName,
                        modifier = Modifier
                            .clickable {
                                viewModel.process(NavigateToSectionDetails(item.id ?: 0, false))
                            })

                    if (state.isAdmin) {
                        Icon(Icons.Default.Delete, "",
                            modifier = Modifier
                                .clickable {
                                    viewModel.process(
                                        DeleteSportSectionWithDetails(item)
                                    )
                                })
                    }
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    viewModel.process(
                        NavigateToAuth
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4080f0)
                )
            ) {
                Text("Back")
            }

            if (state.isAdmin) {
                Icon(Icons.Default.AddCircle, "",
                    modifier = Modifier
                        .clickable {
                            viewModel.process(
                                NavigateToSectionDetails(isAddingItem = true)
                            )
                        })
            }else{
                Text(modifier = Modifier.align(Alignment.Top),
                    text = "Sum: ${state.sum}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp )
            }
        }
    }
}

