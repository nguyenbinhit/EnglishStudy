package com.example.englishstudy.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cau_trac_nghiem")
data class CauTracNghiem(
    @ColumnInfo(name = "id_bo")
    val idBo: Int,

    @ColumnInfo(name = "noi_dung")
    val noiDung: String,

    @ColumnInfo(name = "dap_an_a")
    val dapAnA: String,

    @ColumnInfo(name = "dap_an_b")
    val dapAnB: String,

    @ColumnInfo(name = "dap_an_c")
    val dapAnC: String,

    @ColumnInfo(name = "dap_an_d")
    val dapAnD: String,

    @ColumnInfo(name = "dap_an_dung")
    val dapAnDung: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
