package com.example.englishstudy.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "email")
    var email: String,

    @ColumnInfo(name = "password")
    val password: String,

    @ColumnInfo(name = "point")
    var point: Int,

    @ColumnInfo(name = "role")
    val role: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
