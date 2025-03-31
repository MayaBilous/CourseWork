package com.example.coursework.domain.usecase

import com.example.coursework.domain.boundary.SportSectionListRepository
import com.example.coursework.domain.entity.SectionDetails
import com.example.coursework.domain.entity.SportSection

interface ChangeDetails {
    suspend operator fun invoke(details: SectionDetails)
}

class ChangeDetailsUseCase(
    private val sportSectionListRepository: SportSectionListRepository
) : ChangeDetails {

    override suspend fun invoke(details: SectionDetails) {
        sportSectionListRepository.updateDetails(details)
    }
}