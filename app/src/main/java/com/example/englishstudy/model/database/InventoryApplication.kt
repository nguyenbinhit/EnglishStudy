package com.example.englishstudy.model.database

import android.app.Application
import com.example.englishstudy.model.database.StudyEnglishDB

class InventoryApplication : Application() {
    val database: StudyEnglishDB by lazy { StudyEnglishDB.getInstance(this) }
}