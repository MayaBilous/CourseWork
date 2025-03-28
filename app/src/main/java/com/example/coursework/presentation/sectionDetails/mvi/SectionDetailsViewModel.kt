package com.example.coursework.presentation.sectionDetails.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursework.domain.entity.SectionDetails
import com.example.coursework.domain.entity.SportSection
import com.example.coursework.domain.usecase.CheckSectionDetails
import com.example.coursework.domain.usecase.GetSectionDetails
import com.example.coursework.domain.usecase.UpsertSection
import com.example.coursework.presentation.sectionDetails.mvi.DetailsSportsSectionsViewModel.SectionDetailsUserIntent.NavigateToSectionList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailsSportsSectionsViewModel(
    val sectionId: Long = 0,
    val isAdmin: Boolean,
    private val getSectionDetails: GetSectionDetails,
    val isAddingItem: Boolean,
    val checkSectionDetails: CheckSectionDetails,
    private val upsertSection: UpsertSection,
) : ViewModel() {

    private var _state = MutableStateFlow(
        SectionsDetailsListState(
            sectionId = sectionId,
            isAdmin = isAdmin,
            isAddingItem = isAddingItem,
            sportSection = SportSection.default,
            sectionDetails = SectionDetails.default,
        )
    )
    val state: StateFlow<SectionsDetailsListState>
        get() = _state.asStateFlow()

    private val _userIntent = MutableSharedFlow<SectionDetailsUserIntent>()

    private val _event = MutableSharedFlow<SectionDetailsEvent>()
    val event: SharedFlow<SectionDetailsEvent>
        get() = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            _userIntent.collectLatest { intent ->
                when (intent) {
                    is NavigateToSectionList -> navigateToSectionList()
                    is SectionDetailsUserIntent.ChangeAddress -> changeAddress(address = intent.address)
                    is SectionDetailsUserIntent.ChangePhoneNumber -> changePhoneNumber(phoneNumber = intent.phoneNumber)
                    is SectionDetailsUserIntent.ChangeSectionName -> changeSectionName(sectionName = intent.sectionName)
                    is SectionDetailsUserIntent.ChangeWorkingDays -> changeWorkingDays(workingDays = intent.workingDays)
                    is SectionDetailsUserIntent.ChangePrice -> changePrice(price = intent.price)
                    is SectionDetailsUserIntent.UpdateSportSection -> updateSportSection(intent.sportSection.copy(sectionDetails = listOf(state.value.sectionDetails)))
                }
            }
        }
        if (!isAddingItem) {
            viewModelScope.launch { loadSportSectionDetails() }
        }
    }

    private suspend fun updateSportSection(sportSection: SportSection) {

        val result = checkSectionDetails(sportSection)
        if (result) {
            upsertSection(sportSection)
            _event.emit(SectionDetailsEvent.Navigate(isAdmin = isAdmin))
        } else {
            _event.emit(SectionDetailsEvent.EmptyData)
        }
    }

    private suspend fun loadSportSectionDetails() {
        val sectionDetails = getSectionDetails.invoke(sectionId)
        _state.emit(state.value.copy(sportSection = sectionDetails))
        _state.emit(state.value.copy(sectionDetails = sectionDetails.sectionDetails.find { it.sectionId == sectionId }!!))
    }

    private suspend fun changeSectionName(sectionName: String) {
        _state.emit(
            state.value.copy(
                sportSection = _state.value.sportSection.copy(sectionName = sectionName)
            )
        )
    }

    private suspend fun changePrice(price: Int) {
        _state.emit(
            state.value.copy(
                sectionDetails = _state.value.sectionDetails.copy(price = price)
            )
        )
    }

    private suspend fun changeAddress(address: String) {
        _state.emit(
            state.value.copy(
                sectionDetails = _state.value.sectionDetails.copy(address = address)
            )
        )
    }

    private suspend fun changePhoneNumber(phoneNumber: String) {
        _state.emit(
            state.value.copy(
                sectionDetails = _state.value.sectionDetails.copy(phoneNumber = phoneNumber)
            )
        )
    }

    private suspend fun changeWorkingDays(workingDays: String) {
        _state.emit(
            state.value.copy(
                sectionDetails = _state.value.sectionDetails.copy(workingDays = workingDays)
            )
        )
    }

    fun process(userIntent: SectionDetailsUserIntent) {
        viewModelScope.launch {
            _userIntent.emit(userIntent)
        }
    }

    private suspend fun navigateToSectionList() {
        _event.emit(SectionDetailsEvent.Navigate(isAdmin = state.value.isAdmin))
    }

    data class SectionsDetailsListState(
        val sectionId: Long,
        val isAdmin: Boolean,
        val isAddingItem: Boolean,
        val sportSection: SportSection,
        val sectionDetails: SectionDetails,
    ) {
    }

    sealed interface SectionDetailsUserIntent {
        data class ChangeSectionName(val sectionName: String) : SectionDetailsUserIntent
        data class ChangeAddress(val address: String) : SectionDetailsUserIntent
        data class ChangeWorkingDays(val workingDays: String) : SectionDetailsUserIntent
        data class ChangePhoneNumber(val phoneNumber: String) : SectionDetailsUserIntent
        data class ChangePrice(val price: Int) : SectionDetailsUserIntent
        data class UpdateSportSection(val sportSection: SportSection) : SectionDetailsUserIntent
        data object NavigateToSectionList : SectionDetailsUserIntent
    }

    sealed interface SectionDetailsEvent {
        data class Navigate(val isAdmin: Boolean) : SectionDetailsEvent
        data object EmptyData : SectionDetailsEvent
    }

}