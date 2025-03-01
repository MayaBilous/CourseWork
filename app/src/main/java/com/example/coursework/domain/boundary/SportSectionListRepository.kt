//package com.example.coursework.domain.boundary
//
//import com.example.coursework.data.db.model.DbSportSection
//import com.example.coursework.domain.entity.SportSection
//
//
//interface SportSectionListRepository {
//    suspend fun getSportSection(): List<SportSection>
//    suspend fun getDbSportSection(): List<DbSportSection>
//}
package com.example.coursework.domain.boundary

import com.example.coursework.domain.entity.SportSection


interface SportSectionListRepository {
    suspend fun getList(): MutableList<SportSection>
}