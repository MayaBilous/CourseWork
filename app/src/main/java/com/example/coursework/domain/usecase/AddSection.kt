package com.example.coursework.domain.usecase

import com.example.coursework.domain.boundary.SportSectionListRepository
import com.example.coursework.domain.entity.SportSection

interface AddSection {
    suspend operator fun invoke(sportSection: SportSection)
}

class AddSectionUseCase(
    private val sportSectionListRepository: SportSectionListRepository
) : AddSection {

    override suspend fun invoke(sportSection: SportSection) {
        sportSectionListRepository.addSection(sportSection)
    }
}