package com.example.coursework.domain.usecase

import com.example.coursework.domain.boundary.SportSectionListRepository
import com.example.coursework.domain.entity.SportSection

interface GetSectionList {

    suspend operator fun invoke(): List<SportSection>
}

class GetSectionListUseCase(
    private val sportSectionListRepository: SportSectionListRepository
) : GetSectionList {

    override suspend fun invoke(): List<SportSection> {
        return sportSectionListRepository.getSportSections()
    }
}