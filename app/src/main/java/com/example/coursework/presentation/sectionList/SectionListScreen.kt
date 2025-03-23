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
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.coursework.domain.entity.SectionDetails
import com.example.coursework.presentation.root.AuthNavigation
import com.example.coursework.presentation.root.SectionDetailsNavigation
import com.example.coursework.presentation.sectionDetails.mvi.DetailsSportsSectionsViewModel.SectionDetailsUserIntent
import com.example.coursework.presentation.sectionList.mvi.SectionListViewModel
import com.example.coursework.presentation.sectionList.mvi.SectionListViewModel.SectionListEvent
import com.example.coursework.presentation.sectionList.mvi.SectionListViewModel.SectionListUserIntent.DeleteSectionDetails
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
    val expandedItems = remember { mutableStateMapOf<Long, Boolean>() }

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
                                expandedItems[item.id ?: -1] =
                                    !(expandedItems[item.id ?: -1] ?: false)
                            })

                    DropdownMenu(
                        expanded = expandedItems[item.id ?: -1] == true,
                        onDismissRequest = { expandedItems[item.id ?: -1] = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        item.sectionDetails.forEach { detailsItem ->
                            DropdownMenuItem({
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = detailsItem.address,
                                        modifier = Modifier.weight(1f)
                                    )

                                    if (state.isAdmin) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete",
                                            modifier = Modifier
                                                .clickable {
                                                    viewModel.process(
                                                        DeleteSectionDetails(
                                                            detailsItem.detailsId ?: -1
                                                        )
                                                    )
                                                }
                                                .padding(start = 8.dp)
                                        )
                                    }
                                }
                            },
                                onClick = {
                                    viewModel.process(NavigateToSectionDetails(item.id ?: 0, false))
                                    expandedItems[item.id ?: -1] = false
                                }
                            )
                        }
                    }


                    if (state.isAdmin) {
                        Icon(Icons.Default.Delete, "",
                            modifier = Modifier
                                .clickable {
                                    viewModel.process(
                                        DeleteSportSectionWithDetails(item)
                                    )
                                })
                    }

//                    if (expandedItem == item.id) {
//                        expandedMenu(
//                            isAdmin = state.isAdmin,
//                            sectionDetails = item.sectionDetails,
//                            onDeleteClick = { viewModel.process(DeleteSectionDetails(it)) },
//                            navigation = {
//                                viewModel.process(
//                                    NavigateToSectionDetails(
//                                        item.id,
//                                        isAddingItem = false
//                                    )
//                                )
//                            }
//                        )
//                    }
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
            }
        }
    }
}

//@Composable
//private fun expandedMenu(
//    isAdmin: Boolean,
//    sectionDetails: List<SectionDetails>,
//    onDeleteClick: (Long) -> Unit,
//    navigation: () -> Unit
//) {

//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(Color(0xFF4080f0))
//    ) {
//        sectionDetails.forEach { detailsItem ->
//            Row(
//                horizontalArrangement = Arrangement.SpaceBetween,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text(text = detailsItem.address,
//                    modifier = Modifier
//                        .clickable {
//                            navigation()
//                        })
//
//                if (isAdmin) {
//                    Icon(
//                        imageVector = Icons.Default.Delete,
//                        contentDescription = "Delete",
//                        modifier = Modifier
//                            .clickable {
//                                onDeleteClick(detailsItem.detailsId!!)
//                            }
//                    )
//                }
//            }
//        }
//    }
//}

