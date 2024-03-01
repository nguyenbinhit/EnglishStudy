package com.example.englishstudy.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.Nullable

@Entity(tableName = "tu_vung")
data class TuVung(
    @ColumnInfo(name = "id_bo")
    val idBo: Int,

    @ColumnInfo(name = "dap_an")
    val dapAn: String,

    @ColumnInfo(name = "dich_nghia")
    val dichNghia: String,

    @ColumnInfo(name = "loai_tu")
    val loaiTu: String,

    @ColumnInfo(name = "audio")
    val audio: String,

    @ColumnInfo(name = "anh")
    val anh: ByteArray?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TuVung

        if (anh != null) {
            if (other.anh == null) return false
            if (!anh.contentEquals(other.anh)) return false
        } else if (other.anh != null) return false

        return true
    }

    override fun hashCode(): Int {
        return anh?.contentHashCode() ?: 0
    }
}
