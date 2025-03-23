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
import com.example.coursework.domain.usecase.CheckSectionDetailsUseCase
import com.example.coursework.domain.usecase.DeleteDetailsUseCase
import com.example.coursework.domain.usecase.DeleteSectionWithDetailsUseCase
import com.example.coursework.domain.usecase.GetSectionDetailsUseCase
import com.example.coursework.domain.usecase.GetSectionListUseCase
import com.example.coursework.domain.usecase.UpsertSectionUseCase
import com.example.coursework.presentation.auth.Authorization
import com.example.coursework.presentation.auth.mvi.AuthViewModel
import com.example.coursework.presentation.sectionDetails.SectionDetailsScreen
import com.example.coursework.presentation.sectionDetails.mvi.DetailsSportsSectionsViewModel
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
                        DeleteSectionWithDetailsUseCase(
                            sportSectionListRepository = SportSectionListRepositoryImpl()
                        ),
                        DeleteDetailsUseCase(
                            sportSectionListRepository = SportSectionListRepositoryImpl()
                        )
                    )
                })
            )
        }

        composable<SectionDetailsNavigation> {
            val arg = it.toRoute<SectionDetailsNavigation>()
            SectionDetailsScreen(
                navController = navController,
                viewModel = viewModel(factory = viewModelFactory {
                    DetailsSportsSectionsViewModel(
                        arg.sectionId,
                        arg.isAdmin,

                        GetSectionDetailsUseCase(
                            sportSectionListRepository = SportSectionListRepositoryImpl()
                        ),
                        arg.isAddingItem,
                        CheckSectionDetailsUseCase(),
                        UpsertSectionUseCase(
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
data class SectionDetailsNavigation(
    val sectionId: Long,
    val isAdmin: Boolean,
    val isAddingItem: Boolean
)

inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
    }
