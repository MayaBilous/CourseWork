package com.example.coursework.domain.usecase

import com.example.coursework.domain.boundary.SportSectionListRepository
import com.example.coursework.domain.entity.SportSection

interface ChangeSection {
    suspend operator fun invoke(sportSection: SportSection)
}

class ChangeSectionUseCase(
    private val sportSectionListRepository: SportSectionListRepository
) : ChangeSection {

    override suspend fun invoke(sportSection: SportSection) {
        sportSectionListRepository.updateSection(sportSection)
    }
}