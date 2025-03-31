package com.example.coursework.domain.usecase

import com.example.coursework.domain.boundary.SportSectionListRepository
import com.example.coursework.domain.entity.SectionDetails
import com.example.coursework.domain.entity.SportSection

interface AddDetails {
    suspend operator fun invoke(sectionId: Long, details: SectionDetails)
}

class AddDetailsUseCase(
    private val sportSectionListRepository: SportSectionListRepository
) : AddDetails {

    override suspend fun invoke(sectionId: Long, details: SectionDetails) {
        sportSectionListRepository.addDetails(sectionId, details)
    }
}