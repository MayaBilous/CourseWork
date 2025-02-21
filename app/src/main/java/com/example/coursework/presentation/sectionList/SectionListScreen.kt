package com.example.coursework.presentation.sectionList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.coursework.presentation.root.AuthNavigation
import com.example.coursework.presentation.root.SectionDetailsNavigation
import com.example.coursework.presentation.sectionList.mvi.SectionListViewModel
import com.example.coursework.presentation.sectionList.mvi.SectionListViewModel.SectionListEvent
import com.example.coursework.presentation.sectionList.mvi.SectionListViewModel.SectionListUserIntent.*
import kotlinx.coroutines.flow.collectLatest

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
                        isAdmin = state.isAdmin
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
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {

            TextField(modifier = Modifier
                .width(90.dp)
                .height(25.dp),
                value = state.searchText,
                onValueChange = { viewModel.process(InputSearchText(it)) },
                label = { Text("Enter text", fontSize = 12.sp) })

            Button(onClick = { viewModel.process(Sorting) }) {
                Text(state.sortButtonText)
            }

        }

        LazyColumn(
            modifier = Modifier
        ) {
            items(state.uiSportSection) { item ->
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(text = item.name,
                        modifier = Modifier
                            .clickable {
                                    viewModel.process(
                                        NavigateToSectionDetails(item.id)
                                    )
                            })

                    if (state.isAdmin) {
                        Icon(Icons.Default.Delete, "",
                            modifier = Modifier
                                .clickable { viewModel.process(DeleteElement(item.id)) })
                    }
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { viewModel.process(
                NavigateToAuth
            ) }) {
                Text("Back")
            }

            if (state.isAdmin) {
                Icon(Icons.Default.AddCircle, "")
            }
        }
    }
}