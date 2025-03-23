package com.example.coursework.domain.usecase

import com.example.coursework.domain.boundary.SportSectionListRepository
import com.example.coursework.domain.entity.SportSection

interface UpsertSection {
    suspend operator fun invoke(sportSection: SportSection)
}

class UpsertSectionUseCase(
    private val sportSectionListRepository: SportSectionListRepository
) : UpsertSection {

    override suspend fun invoke(sportSection: SportSection) {
        sportSectionListRepository.upsert(sportSection)
    }
}