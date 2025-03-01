package com.example.coursework.domain.usecase


import com.example.coursework.domain.boundary.SportSectionListRepository
import com.example.coursework.domain.entity.SportSection

interface DeleteSection {
    suspend operator fun invoke(sectionId: Long)
}

class DeleteSectionUseCase(
    private val sportSectionListRepository: SportSectionListRepository
) : DeleteSection {

    override suspend fun invoke(sectionId: Long) {
        sportSectionListRepository.delete(sectionId)
    }
}