package com.example.englishstudy.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cau_luyen_nghe")
data class CauLuyenNghe(
    @ColumnInfo(name = "id_bo")
    val idBo: Int,

    @ColumnInfo(name = "dap_an_a")
    val dapAnA: String,

    @ColumnInfo(name = "dap_an_b")
    val dapAnB: String,

    @ColumnInfo(name = "dap_an_c")
    val dapAnC: String,

    @ColumnInfo(name = "dap_an_d")
    val dapAnD: String,

    @ColumnInfo(name = "dap_an_dung")
    val dapAnDung: String,

    @ColumnInfo(name = "hinh_anh")
    val hinhAnh: ByteArray,

    @ColumnInfo(name = "audio")
    val audio: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CauLuyenNghe

        return hinhAnh.contentEquals(other.hinhAnh)
    }

    override fun hashCode(): Int {
        return hinhAnh.contentHashCode()
    }
}
