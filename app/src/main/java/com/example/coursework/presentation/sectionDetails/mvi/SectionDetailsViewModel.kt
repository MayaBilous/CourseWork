package com.example.coursework.presentation.sectionDetails.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursework.domain.entity.SportSections
import com.example.coursework.domain.usecase.DownloadSectionDetails
import com.example.coursework.presentation.sectionDetails.mvi.InformationAboutSportsSectionsViewModel.SectionsInfoUserIntent.NavigateToSectionList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class InformationAboutSportsSectionsViewModel(
    val sectionId: Int = 0,
    val isAdmin: Boolean,
    private val downloadSectionDetails: DownloadSectionDetails,
    val isAddingItem: Boolean
) : ViewModel() {

    private var _state = MutableStateFlow(
        SectionsDetailsListState(
            sectionId = sectionId,
            isAdmin = isAdmin,
            isAddingItem = isAddingItem,
            sportSections = SportSections.default
        )
    )
    val state: StateFlow<SectionsDetailsListState>
        get() = _state.asStateFlow()

    private val _userIntent = MutableSharedFlow<SectionsInfoUserIntent>()

    private val _event = MutableSharedFlow<SectionsInfoEvent>()
    val event: SharedFlow<SectionsInfoEvent>
        get() = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            _userIntent.collectLatest { intent ->
                when (intent) {
                    is NavigateToSectionList -> navigateToSectionList()
                    is SectionsInfoUserIntent.ChangeAddress -> changeAddress(address = intent.address)
                    is SectionsInfoUserIntent.ChangePhoneNumber -> changePhoneNumber(phoneNumber = intent.phoneNumber)
                    is SectionsInfoUserIntent.ChangeSectionName -> changeSectionName(sectionName = intent.sectionName)
                    is SectionsInfoUserIntent.ChangeWorkingDays -> changeWorkingDays(workingDays = intent.workingDays)
                }
            }
        }
        viewModelScope.launch { loadSportSectionDetails() }
    }

    private suspend fun loadSportSectionDetails() {
        val sectionDetails = downloadSectionDetails.invoke(sectionId)
        _state.emit(state.value.copy(sportSections = sectionDetails))
    }

    private suspend fun changeSectionName(sectionName: String) {
        _state.emit(
            state.value.copy(
                sportSections = _state.value.sportSections.copy(name = sectionName)
            )
        )
    }

    private suspend fun changeAddress(address: String) {
        _state.emit(
            state.value.copy(
                sportSections = _state.value.sportSections.copy(address = address)
            )
        )
    }

    private suspend fun changePhoneNumber(phoneNumber: Int) {
        _state.emit(
            state.value.copy(
                sportSections = _state.value.sportSections.copy(phoneNumber = phoneNumber)
            )
        )
    }

    private suspend fun changeWorkingDays(workingDays: String) {
        _state.emit(
            state.value.copy(
                sportSections = _state.value.sportSections.copy(workingDays = workingDays)
            )
        )
    }

    fun process(userIntent: SectionsInfoUserIntent) {
        viewModelScope.launch {
            _userIntent.emit(userIntent)
        }
    }

    private suspend fun navigateToSectionList() {
        _event.emit(SectionsInfoEvent.Navigate(isAdmin = state.value.isAdmin))
    }

    data class SectionsDetailsListState(
        val sectionId: Int,
        val isAdmin: Boolean,
        val isAddingItem: Boolean,
        val sportSections: SportSections
    ) {
    }

    sealed interface SectionsInfoUserIntent {
        data class ChangeSectionName(val sectionName: String) : SectionsInfoUserIntent
        data class ChangeAddress(val address: String) : SectionsInfoUserIntent
        data class ChangeWorkingDays(val workingDays: String) : SectionsInfoUserIntent
        data class ChangePhoneNumber(val phoneNumber: Int) : SectionsInfoUserIntent
        data object NavigateToSectionList : SectionsInfoUserIntent
    }

    sealed interface SectionsInfoEvent {
        data class Navigate(val isAdmin: Boolean) : SectionsInfoEvent
    }

}