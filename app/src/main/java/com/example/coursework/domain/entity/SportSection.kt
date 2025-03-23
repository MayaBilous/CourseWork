package com.example.coursework.domain.entity

data class SportSection(
    val id: Long?,
    val sectionName: String,
    val sectionDetails: List<SectionDetails>
    ) {

    companion object {
        val default = SportSection(
            id = 0,
            sectionName = "",
            sectionDetails = emptyList(),
        )
    }
}
