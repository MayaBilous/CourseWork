package com.example.coursework.domain.usecase

import com.example.coursework.domain.boundary.SportSectionListRepository
import com.example.coursework.domain.entity.SportSections

interface GetSectionList {

    suspend operator fun invoke(): List<SportSections>
}

class GetSectionListUseCase(
    private val sportSectionListRepository: SportSectionListRepository
) : GetSectionList {

    override suspend fun invoke() :List<SportSections> {
        return sportSectionListRepository.getList()
    }
}
