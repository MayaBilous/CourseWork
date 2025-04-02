package com.example.coursework.presentation.sectionDetails.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursework.domain.entity.SectionDetails
import com.example.coursework.domain.entity.SportSection
import com.example.coursework.domain.usecase.AddSection
import com.example.coursework.domain.usecase.ChangeSection
import com.example.coursework.domain.usecase.CheckSectionName
import com.example.coursework.domain.usecase.DeleteDetails
import com.example.coursework.domain.usecase.GetSectionDetails
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
    val sectionId: Long? = null,
    val isAdmin: Boolean,
    private val getSectionDetails: GetSectionDetails,
    val isAddingItem: Boolean,
    val changeSection: ChangeSection,
    val addSection: AddSection,
    val checkSectionName: CheckSectionName,
    private val deleteDetails: DeleteDetails,
) : ViewModel() {

    private var _state = MutableStateFlow(
        SectionsDetailsListState(
            sectionId = sectionId,
            isAdmin = isAdmin,
            isAddingItem = isAddingItem,
            sportSection = SportSection.default,
            sectionDetails = SectionDetails.default,
            detailsId = null,
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
                    is SectionDetailsUserIntent.ChangeSectionName -> changeSectionName(sectionName = intent.sectionName)
                    is SectionDetailsUserIntent.AddSportSection -> addSportSectionWithDetails(intent.sportSection)
                    is SectionDetailsUserIntent.DeleteSectionDetails -> deleteDetails(intent.detailsId)
                    is SectionDetailsUserIntent.NavigateToClub -> navigateToClub(
                        sectionId = intent.sectionId,
                        isAddingItem = intent.isAddingItem,
                        detailsId = intent.detailsId,
                    )

                    is SectionDetailsUserIntent.UpdateSection -> updateSportSection(intent.sportSection)
                }
            }
        }
        if (!isAddingItem) {
            viewModelScope.launch { loadSportSectionDetails() }
        }
    }

    private suspend fun updateSportSection(sportSection: SportSection) {
        val result = checkSectionName(sportSection)
        if (result) {
            changeSection(sportSection)
            _event.emit(SectionDetailsEvent.NavigateToSectionList(isAdmin = isAdmin))
        } else {
            _event.emit(SectionDetailsEvent.EmptyData)
        }
    }

    private suspend fun addSportSectionWithDetails(sportSection: SportSection) {
        val result = checkSectionName(sportSection)
        if (result) {
            addSection(sportSection)
            _event.emit(SectionDetailsEvent.NavigateToSectionList(isAdmin = isAdmin))
        } else {
            _event.emit(SectionDetailsEvent.EmptyData)
        }
    }

    private suspend fun loadSportSectionDetails() {
        val sectionDetails = getSectionDetails.invoke(sectionId ?: 0)
        _state.emit(state.value.copy(sportSection = sectionDetails))
        _state.emit(state.value.copy(sectionDetails = sectionDetails.sectionDetails.find { it.sectionId == sectionId }))
    }

    private suspend fun changeSectionName(sectionName: String) {
        _state.emit(
            state.value.copy(
                sportSection = _state.value.sportSection.copy(sectionName = sectionName)
            )
        )
    }

    private suspend fun navigateToClub(sectionId: Long, isAddingItem: Boolean, detailsId: Long?) {
        _event.emit(
            SectionDetailsEvent.NavigateToClub(
                isAdmin = state.value.isAdmin,
                isAddingItem = isAddingItem,
                sectionId = sectionId,
                detailsId = detailsId
            )
        )
    }


    private suspend fun deleteDetails(detailsId: Long) {
        deleteDetails.invoke(detailsId)
        loadSportSectionDetails()
    }

    fun process(userIntent: SectionDetailsUserIntent) {
        viewModelScope.launch {
            _userIntent.emit(userIntent)
        }
    }

    private suspend fun navigateToSectionList() {
        _event.emit(SectionDetailsEvent.NavigateToSectionList(isAdmin = state.value.isAdmin))
    }

    data class SectionsDetailsListState(
        val sectionId: Long?,
        val detailsId: Long?,
        val isAdmin: Boolean,
        val isAddingItem: Boolean,
        val sportSection: SportSection,
        val sectionDetails: SectionDetails?,
    ) {
    }

    sealed interface SectionDetailsUserIntent {
        data class ChangeSectionName(val sectionName: String) : SectionDetailsUserIntent
        data class AddSportSection(val sportSection: SportSection) : SectionDetailsUserIntent
        data object NavigateToSectionList : SectionDetailsUserIntent
        data class NavigateToClub(
            val sectionId: Long,
            val isAddingItem: Boolean,
            val detailsId: Long?
        ) : SectionDetailsUserIntent

        data class DeleteSectionDetails(val detailsId: Long) : SectionDetailsUserIntent
        data class UpdateSection(val sportSection: SportSection) : SectionDetailsUserIntent
    }

    sealed interface SectionDetailsEvent {
        data class NavigateToSectionList(val isAdmin: Boolean) : SectionDetailsEvent
        data class NavigateToClub(
            val sectionId: Long,
            val isAdmin: Boolean,
            val isAddingItem: Boolean,
            val detailsId: Long?
        ) : SectionDetailsEvent

        data object EmptyData : SectionDetailsEvent
    }

}