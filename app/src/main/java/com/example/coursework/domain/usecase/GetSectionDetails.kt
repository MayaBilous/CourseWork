package com.example.coursework.domain.usecase

import com.example.coursework.domain.boundary.SportSectionListRepository
import com.example.coursework.domain.entity.SportSection


interface DownloadSectionDetails {

    suspend operator fun invoke(sectionId: Int): SportSection
}

class DownloadSectionDetailsUseCase(
    private val sportSectionListRepository: SportSectionListRepository
) : DownloadSectionDetails {

    override suspend fun invoke(sectionId: Int) : SportSection {
        return sportSectionListRepository.getList().find { it.id == sectionId } ?: SportSection.default
    }
}
