package com.example.coursework.domain.usecase

import com.example.coursework.domain.boundary.SportSectionListRepository
import com.example.coursework.domain.entity.SportSection

interface InsertSection {
    suspend operator fun invoke(sportSection: SportSection)
}

class InsertSectionUseCase(
    private val sportSectionListRepository: SportSectionListRepository
) : InsertSection {

    override suspend fun invoke(sportSection: SportSection) {
        sportSectionListRepository.insert(sportSection)
    }
}