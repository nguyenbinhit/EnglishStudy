package com.example.englishstudy.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.englishstudy.model.database.StudyEnglishDB
import com.example.englishstudy.model.entity.BoHocTap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BoHocTapRepository(val context: Context) {
    private val studyEnglishDB = StudyEnglishDB.getInstance(context)
    private val boHocTapDao = studyEnglishDB.boHocTapDao()

    // get all bo hoc tap
    public fun getListBoHocTap() = boHocTapDao.getListBoHocTap()

    // create bo hoc tap
    public suspend fun createBoHocTap(boHocTap: BoHocTap) {
        withContext(Dispatchers.IO) {
            boHocTapDao.insertBoHocTap(boHocTap)
        }
    }

    // get bo hoc tap detail
    public fun getBoHocTapDetail(id: Int) : BoHocTap {
        return boHocTapDao.getBoHocTapDetail(id)
    }

    // update bo hoc tap
    public suspend fun updateBoHocTap(boHocTap: BoHocTap) {
        withContext(Dispatchers.IO) {
            boHocTapDao.updateBoHocTap(boHocTap)
        }
    }

    // delete bo hoc tap
    public suspend fun deleteBoHocTap(boHocTap: BoHocTap) {
        withContext(Dispatchers.IO) {
            boHocTapDao.deleteBoHocTap(boHocTap)
        }
    }

    // get a specific bo hoc tap
    public fun getBoHocTapAtPosition(position: Int): LiveData<BoHocTap> {
        return boHocTapDao.getBoHocTapAtPosition(position)
    }

    // get max stt bo hoc tap
    public fun getMaxSTTBoHocTap(): Int {
        return boHocTapDao.getMaxSTTBoHocTap()
    }
}