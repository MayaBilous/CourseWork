package com.example.coursework.domain.usecase

import com.example.coursework.domain.boundary.SportSectionListRepository
import com.example.coursework.domain.entity.SportSections


interface DownloadSectionDetails {

    suspend operator fun invoke(sectionId: Int): SportSections
}

class DownloadSectionDetailsUseCase(
    private val sportSectionListRepository: SportSectionListRepository
) : DownloadSectionDetails {

    override suspend fun invoke(sectionId: Int) : SportSections {
        return sportSectionListRepository.getList().find { it.id == sectionId } ?: SportSections.default
    }
}
