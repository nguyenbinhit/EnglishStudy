package com.example.englishstudy.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cau_dien_khuyet")
data class CauDienKhuyet(
    @ColumnInfo(name = "id_bo")
    val idBo: Int,

    @ColumnInfo(name = "noi_dung")
    var noiDung: String,

    @ColumnInfo(name = "dap_an")
    val dapAn: String,

    @ColumnInfo(name = "goi_y")
    var goiY: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    val noidung: String?
        get() {
            TODO()
        }

    val goiy: String?
        get() {
            TODO()
        }

    var dapan: String = ""
        get() {
            TODO()
        }
}
