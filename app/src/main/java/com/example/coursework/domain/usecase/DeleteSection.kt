package com.example.coursework.domain.usecase


import com.example.coursework.domain.boundary.SportSectionListRepository
import com.example.coursework.domain.entity.SportSection

interface DeleteSection {
    suspend operator fun invoke(sectionId: Int): List<SportSection>
}

class DeleteSectionUseCase(
    private val sportSectionListRepository: SportSectionListRepository
) : DeleteSection {

    override suspend fun invoke(sectionId: Int) :List<SportSection> {
        val list = sportSectionListRepository.getList()
        list.removeAll { it.id == sectionId }
        return list
    }
}