package com.example.englishstudy.repository

import android.content.Context
import com.example.englishstudy.model.database.StudyEnglishDB
import com.example.englishstudy.model.entity.TuVung
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TuVungRepository(val context: Context) {
    private val studyEnglishDB = StudyEnglishDB.getInstance(context)
    private val tuVungDao = studyEnglishDB.tuVungDao()

    // get all tu vung
    public suspend fun getListTuVung() = tuVungDao.getListTuVung()

    // get all tu vung by id bo
    public fun getListTuVungByIdBo(idBo: Int) = tuVungDao.getListTuVungByIdBo(idBo)

    // create tu vung
    public suspend fun createTuVung(tuVung: TuVung) {
        withContext(Dispatchers.IO) {
            tuVungDao.insertTuVung(tuVung)
        }
    }

    // get tu vung detail
    public fun getTuVungDetail(id: Int) = tuVungDao.getTuVungById(id)

    // update tu vung
    public suspend fun updateTuVung(tuVung: TuVung) {
        withContext(Dispatchers.IO) {
            tuVungDao.updateTuVung(tuVung)
        }
    }

    // delete tu vung
    public suspend fun deleteTuVung(tuVung: TuVung) {
        withContext(Dispatchers.IO) {
            tuVungDao.deleteTuVung(tuVung)
        }
    }

    // get all tu vung by id
    public fun getListTuVungById(id: Int) = tuVungDao.getTuVungById(id)
}