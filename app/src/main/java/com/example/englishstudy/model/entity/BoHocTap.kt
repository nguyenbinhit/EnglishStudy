package com.example.englishstudy.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bo_hoc_tap")
data class BoHocTap(
    @ColumnInfo(name = "stt")
    val stt: Int,

    @ColumnInfo(name = "ten_bo")
    val tenBo: String,
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
