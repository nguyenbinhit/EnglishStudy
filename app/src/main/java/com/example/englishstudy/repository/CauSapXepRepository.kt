package com.example.englishstudy.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.englishstudy.model.database.StudyEnglishDB
import com.example.englishstudy.model.entity.CauSapXep
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CauSapXepRepository(val context: Context) {
    private val studyEnglishDB = StudyEnglishDB.getInstance(context)
    private val cauSapXepDao = studyEnglishDB.cauSapXepDao()

    // get all cau sap xep
    public suspend fun getListCauSapXep() = cauSapXepDao.getListCauSapXep()

    // get all cau sap xep by id bo
    public fun getListCauSapXepByIdBo(idBo: Int) : LiveData<List<CauSapXep>> {
        return cauSapXepDao.getListCauSapXepByIdBo(idBo)
    }

    // create cau sap xep
    public suspend fun createCauSapXep(cauSapXep: CauSapXep) {
        withContext(Dispatchers.IO) {
            cauSapXepDao.insertCauSapXep(cauSapXep)
        }
    }

    // get cau sap xep detail
    public suspend fun getCauSapXepDetail(id: Int) = cauSapXepDao.getCauSapXepDetail(id)

    // update cau sap xep
    public suspend fun updateCauSapXep(cauSapXep: CauSapXep) {
        withContext(Dispatchers.IO) {
            cauSapXepDao.updateCauSapXep(cauSapXep)
        }
    }

    // delete cau sap xep
    public suspend fun deleteCauSapXep(cauSapXep: CauSapXep) {
        withContext(Dispatchers.IO) {
            cauSapXepDao.deleteCauSapXep(cauSapXep)
        }
    }

    // get cau sap xep by id
    public fun getCauSapXepById(id: Int) = cauSapXepDao.getCauSapXepById(id)
}