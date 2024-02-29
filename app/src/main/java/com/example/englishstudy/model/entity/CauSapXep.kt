package com.example.englishstudy.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cau_sap_xep")
data class CauSapXep(
    @ColumnInfo(name = "id_bo")
    var idBo: Int,

    @ColumnInfo(name = "dap_an")
    var dapAn: String,

    @ColumnInfo(name = "cau_hoi_1")
    var part1: String,

    @ColumnInfo(name = "cau_hoi_2")
    var part2: String,

    @ColumnInfo(name = "cau_hoi_3")
    var part3: String,

    @ColumnInfo(name = "cau_hoi_4")
    var part4: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
