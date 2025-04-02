package com.example.coursework.domain.usecase

import com.example.coursework.domain.boundary.SportSectionListRepository

interface OrderAmount {
    suspend operator fun invoke(): Int
}

class OrderAmountUseCase(
    private val sportSectionListRepository: SportSectionListRepository
) : OrderAmount {

    override suspend fun invoke(): Int {
        val selectedDetails = sportSectionListRepository.getDetails().filter { it.isSelected }
        return selectedDetails.sumOf { it.price }
    }
}