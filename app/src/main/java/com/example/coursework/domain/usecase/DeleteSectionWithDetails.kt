package com.example.coursework.domain.usecase


import com.example.coursework.domain.boundary.SportSectionListRepository
import com.example.coursework.domain.entity.SportSection

interface DeleteSectionWithDetails {
    suspend operator fun invoke(sportSection: SportSection)
}

class DeleteSectionWithDetailsUseCase(
    private val sportSectionListRepository: SportSectionListRepository
) : DeleteSectionWithDetails {

    override suspend fun invoke(sportSection: SportSection) {
        sportSectionListRepository.delete(sportSection)
    }
}