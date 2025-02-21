package com.example.coursework.domain.boundary

import com.example.coursework.domain.entity.SportSections


interface SportSectionListRepository {
    suspend fun getList(): MutableList<SportSections>
}