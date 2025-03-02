package com.example.coursework.presentation.sectionDetails.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursework.domain.entity.SportSection
import com.example.coursework.domain.usecase.GetSectionDetails
import com.example.coursework.domain.usecase.InsertSection
import com.example.coursework.domain.usecase.UpdateSection
import com.example.coursework.presentation.auth.mvi.AuthState
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
    val sectionId: Long = 0,
    val isAdmin: Boolean,
    private val getSectionDetails: GetSectionDetails,
    val isAddingItem: Boolean,
    private val updateSection: UpdateSection,
    private val insertSection: InsertSection,
) : ViewModel() {

    private var _state = MutableStateFlow(
        SectionsDetailsListState(
            sectionId = sectionId,
            isAdmin = isAdmin,
            isAddingItem = isAddingItem,
            sportSection = SportSection.default
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
                    is SectionsInfoUserIntent.UpdateSportSection -> updateSportSection(sportSection = intent.sportSection)
                    is SectionsInfoUserIntent.InsertSportSection -> insertSportSection(sportSection = intent.sportSection)
                    is SectionsInfoUserIntent.ChangeDistrict -> changeDistrict(district = intent.district)
                }
            }
        }
        if (!isAddingItem){
            viewModelScope.launch { loadSportSectionDetails() }
        }
    }

    private suspend fun updateSportSection(sportSection: SportSection) {
        updateSection.invoke(sportSection)
    }

    private suspend fun insertSportSection(sportSection: SportSection) {
        insertSection.invoke(sportSection)
    }

    private suspend fun loadSportSectionDetails() {
        val sectionDetails = getSectionDetails.invoke(sectionId)
        _state.emit(state.value.copy(sportSection = sectionDetails))
    }

    private suspend fun changeSectionName(sectionName: String) {
        _state.emit(
            state.value.copy(
                sportSection = _state.value.sportSection.copy(sectionName = sectionName)
            )
        )
    }

    private suspend fun changeDistrict(district: String) {
        _state.emit(
            state.value.copy(
                sportSection = _state.value.sportSection.copy(district = district)
            )
        )
    }

    private suspend fun changeAddress(address: String) {
        _state.emit(
            state.value.copy(
                sportSection = _state.value.sportSection.copy(address = address)
            )
        )
    }

    private suspend fun changePhoneNumber(phoneNumber: String) {
        _state.emit(
            state.value.copy(
                sportSection = _state.value.sportSection.copy(phoneNumber = phoneNumber)
            )
        )
    }

    private suspend fun changeWorkingDays(workingDays: String) {
        _state.emit(
            state.value.copy(
                sportSection = _state.value.sportSection.copy(workingDays = workingDays)
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
        val sectionId: Long,
        val isAdmin: Boolean,
        val isAddingItem: Boolean,
        val sportSection: SportSection
    ) {
    }

    sealed interface SectionsInfoUserIntent {
        data class ChangeSectionName(val sectionName: String) : SectionsInfoUserIntent
        data class ChangeAddress(val address: String) : SectionsInfoUserIntent
        data class ChangeWorkingDays(val workingDays: String) : SectionsInfoUserIntent
        data class ChangePhoneNumber(val phoneNumber: String) : SectionsInfoUserIntent
        data class ChangeDistrict(val district: String): SectionsInfoUserIntent
        data class UpdateSportSection(val sportSection: SportSection): SectionsInfoUserIntent
        data class InsertSportSection(val sportSection: SportSection): SectionsInfoUserIntent
        data object NavigateToSectionList : SectionsInfoUserIntent
    }

    sealed interface SectionsInfoEvent {
        data class Navigate(val isAdmin: Boolean) : SectionsInfoEvent
    }

}