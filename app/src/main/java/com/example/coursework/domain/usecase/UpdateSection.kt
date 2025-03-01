package com.example.coursework.domain.usecase

import com.example.coursework.domain.boundary.SportSectionListRepository
import com.example.coursework.domain.entity.SportSection

interface UpdateSection {
    suspend operator fun invoke(sportSection: SportSection)
}

class UpdateSectionUseCase(
    private val sportSectionListRepository: SportSectionListRepository
) : UpdateSection {

    override suspend fun invoke(sportSection: SportSection) {
        sportSectionListRepository.update(sportSection)
    }
}