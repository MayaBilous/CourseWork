package com.example.coursework.presentation.sectionList.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursework.domain.entity.SportSection
import com.example.coursework.domain.usecase.DeleteSection
import com.example.coursework.domain.usecase.GetSectionList
import com.example.coursework.presentation.sectionList.mvi.SectionListViewModel.SectionListUserIntent.InputSearchText
import com.example.coursework.presentation.sectionList.mvi.SectionListViewModel.SectionListUserIntent.*
import com.example.coursework.presentation.sectionList.mvi.SectionListViewModel.SectionListUserIntent.Sorting
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SectionListViewModel(
    isAdmin: Boolean,
    private val getSectionList: GetSectionList,
    private val deleteSection: DeleteSection
) : ViewModel() {

    private var _state = MutableStateFlow(SectionListState.default.copy(isAdmin = isAdmin))
    val state: StateFlow<SectionListState>
        get() = _state.asStateFlow()

    private val _userIntent = MutableSharedFlow<SectionListUserIntent>()

    private val _event = MutableSharedFlow<SectionListEvent>()
    val event: SharedFlow<SectionListEvent>
        get() = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            _userIntent.collectLatest { intent ->
                when (intent) {
                    is InputSearchText -> inputSearchText(searchText = intent.searchText)
                    is Sorting -> soring()
                    is NavigateToSectionDetails -> navigateToSectionDetails(intent.sectionId, intent.isAddingItem)
                    is NavigateToAuth -> navigateToAuth()
                    is DeleteElement -> delete(intent.sectionId)
                }
            }
        }
        viewModelScope.launch {loadSportSections()}
    }

    fun process(userIntent: SectionListUserIntent) {
        viewModelScope.launch {
            _userIntent.emit(userIntent)
        }
    }

    private suspend fun loadSportSections() {
            val sections = getSectionList.invoke()
            _state.emit(state.value.copy(sportSections = sections))
        }

    private suspend fun inputSearchText(searchText: String) {
        _state.emit(state.value.copy(searchText = searchText))
    }

    private suspend fun soring() {
        _state.emit(state.value.copy(isSortedAsc = !state.value.isSortedAsc))
    }

    private suspend fun delete(sectionId: Long) {
        deleteSection.invoke(sectionId)
        _state.emit(state.value.copy(sportSections = getSectionList.invoke()))
    }

    private suspend fun navigateToSectionDetails(sectionId: Long, isAddingItem: Boolean) {
        _event.emit(
            SectionListEvent.NavigateToSD(
                sportSectionId = sectionId,
                isAdmin = state.value.isAdmin,
                isAddingItem = isAddingItem,
            )
        )

    }

    private suspend fun navigateToAuth() {
        _event.emit(
            SectionListEvent.NavigateToA
        )

    }

    data class SectionListState(
        val isAdmin: Boolean,
        val searchText: String,
        val sportSections: List<SportSection>,
        val isSortedAsc: Boolean,
    ) {
        val uiSportSection: List<SportSection>
            get() = sportSections
                .filter {
                    if (searchText.isEmpty()) {
                        true
                    } else {
                        it.sectionName.contains(searchText)
                    }
                }
                .let { list ->
                    if (isSortedAsc) {
                        list.sortedBy { it.sectionName }
                    } else {
                        list.sortedByDescending { it.sectionName }
                    }
                }
        val sortButtonText: String
            get() = if (isSortedAsc) {
                "Sort Asc"
            } else {
                "Sort Desc"
            }

        companion object {
            val default = SectionListState(
                isAdmin = false,
                searchText = "",
                sportSections = emptyList(),
                isSortedAsc = true,
            )
        }
    }

    sealed interface SectionListUserIntent {
        data class InputSearchText(val searchText: String) : SectionListUserIntent
        data object Sorting : SectionListUserIntent
        data class NavigateToSectionDetails(val sectionId: Long = 0, val isAddingItem: Boolean) : SectionListUserIntent
        data object NavigateToAuth : SectionListUserIntent
        data class DeleteElement(val sectionId: Long): SectionListUserIntent
    }

    sealed interface SectionListEvent {
        data class NavigateToSD(val sportSectionId: Long, val isAdmin: Boolean, val isAddingItem: Boolean) : SectionListEvent
        data object NavigateToA: SectionListEvent
    }
}