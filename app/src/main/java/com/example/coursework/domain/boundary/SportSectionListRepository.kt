package com.example.coursework.domain.boundary

import com.example.coursework.domain.entity.SportSection


interface SportSectionListRepository {
    suspend fun getList(): MutableList<SportSection>
}