package com.example.coursework.domain.usecase

import com.example.coursework.domain.boundary.SportSectionListRepository
import com.example.coursework.domain.entity.SportSection

interface DeleteDetails {
    suspend operator fun invoke(detailsId: Long)
}

class DeleteDetailsUseCase(
    private val sportSectionListRepository: SportSectionListRepository
) : DeleteDetails {

    override suspend fun invoke(detailsId: Long) {
        sportSectionListRepository.deleteDetails(detailsId)
    }
}