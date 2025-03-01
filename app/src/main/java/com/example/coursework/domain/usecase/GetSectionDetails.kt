package com.example.coursework.domain.usecase

import com.example.coursework.domain.boundary.SportSectionListRepository
import com.example.coursework.domain.entity.SportSection


interface GetSectionDetails {

    suspend operator fun invoke(sectionId: Long): SportSection
}

class DownloadSectionDetailsUseCase(
    private val sportSectionListRepository: SportSectionListRepository
) : GetSectionDetails {

    override suspend fun invoke(sectionId: Long) : SportSection {
        return sportSectionListRepository.getSportSection().find { it.id == sectionId } ?: SportSection.default
    }
}
