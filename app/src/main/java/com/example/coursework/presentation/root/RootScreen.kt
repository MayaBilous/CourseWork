package com.example.coursework.presentation.root

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.coursework.data.repository.AuthorizationRepositoryImpl
import com.example.coursework.data.repository.SportSectionListRepositoryImpl
import com.example.coursework.domain.usecase.CheckAuthorizationUseCase
import com.example.coursework.domain.usecase.DeleteSectionUseCase
import com.example.coursework.domain.usecase.DownloadSectionDetailsUseCase
import com.example.coursework.domain.usecase.GetSectionListUseCase
import com.example.coursework.domain.usecase.InsertSectionUseCase
import com.example.coursework.domain.usecase.UpdateSectionUseCase
import com.example.coursework.presentation.auth.Authorization
import com.example.coursework.presentation.auth.mvi.AuthViewModel
import com.example.coursework.presentation.sectionDetails.InformationAboutSportsSections
import com.example.coursework.presentation.sectionDetails.mvi.InformationAboutSportsSectionsViewModel
import com.example.coursework.presentation.sectionList.SectionListScreen
import com.example.coursework.presentation.sectionList.mvi.SectionListViewModel
import kotlinx.serialization.Serializable

@Composable
fun RootScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AuthNavigation) {
        composable<AuthNavigation> {
            Authorization(
                navController = navController,
                viewModel = viewModel(
                    factory = viewModelFactory {
                        AuthViewModel(
                            CheckAuthorizationUseCase(
                                authorizationRepository = AuthorizationRepositoryImpl()
                            )
                        )
                    }
                )
            )
        }
        composable<SectionsListNavigation> {
            val arg = it.toRoute<SectionsListNavigation>()
            SectionListScreen(
                navController = navController,
                viewModel = viewModel(factory = viewModelFactory {
                    SectionListViewModel(
                        arg.isAdmin,
                        GetSectionListUseCase(
                            sportSectionListRepository = SportSectionListRepositoryImpl()
                        ),
                        DeleteSectionUseCase(
                            sportSectionListRepository = SportSectionListRepositoryImpl()
                        )
                    )
                })
            )
        }

        composable<SectionDetailsNavigation> {
            val arg = it.toRoute<SectionDetailsNavigation>()
            InformationAboutSportsSections(
                navController = navController,
                viewModel = viewModel(factory = viewModelFactory {
                    InformationAboutSportsSectionsViewModel(
                        arg.sectionId,
                        arg.isAdmin,

                        DownloadSectionDetailsUseCase(
                            sportSectionListRepository = SportSectionListRepositoryImpl()
                        ),
                        arg.isAddingItem,
                        UpdateSectionUseCase(
                            sportSectionListRepository = SportSectionListRepositoryImpl()
                        ),
                        InsertSectionUseCase(
                            sportSectionListRepository = SportSectionListRepositoryImpl()
                        ),
                    )
                })
            )
        }
    }
}


@Serializable
data object AuthNavigation

@Serializable
data class SectionsListNavigation(val isAdmin: Boolean)

@Serializable
data class SectionDetailsNavigation(val sectionId: Long, val isAdmin: Boolean, val isAddingItem: Boolean)

inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
    }
