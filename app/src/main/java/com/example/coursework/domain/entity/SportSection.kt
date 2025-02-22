package com.example.coursework.domain.entity

data class SportSection(
    val id: Int,
    val name: String,
    val address: String,
    val workingDays: String,
    val phoneNumber: Int,
    ) {

    companion object {
        val default = SportSection(
            id = 0,
            name = "",
            address = "",
            workingDays = "",
            phoneNumber = 0,
        )
    }
}
