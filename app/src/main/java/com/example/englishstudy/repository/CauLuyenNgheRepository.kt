package com.example.englishstudy.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.englishstudy.model.database.StudyEnglishDB
import com.example.englishstudy.model.entity.CauLuyenNghe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CauLuyenNgheRepository(val context: Context) {
    private val studyEnglishDB = StudyEnglishDB.getInstance(context)
    private val cauLuyenNgheDao = studyEnglishDB.cauLuyenNgheDao()

    // get all cau luyen nghe
    public suspend fun getListCauLuyenNghe() = cauLuyenNgheDao.getListCauLuyenNghe()

    // get all cau luyen nghe by id bo
    public fun getListCauLuyenNgheByIdBo(idBo: Int): LiveData<List<CauLuyenNghe>> {
        return cauLuyenNgheDao.getListCauLuyenNgheByIdBo(idBo)
    }

    // create cau luyen nghe
    public suspend fun createCauLuyenNghe(cauLuyenNghe: CauLuyenNghe) {
        withContext(Dispatchers.IO) {
            cauLuyenNgheDao.insertCauLuyenNghe(cauLuyenNghe)
        }
    }

    // get cau luyen nghe detail
    public suspend fun getCauLuyenNgheDetail(id: Int) = cauLuyenNgheDao.getCauLuyenNgheDetail(id)

    // update cau luyen nghe
    public suspend fun updateCauLuyenNghe(cauLuyenNghe: CauLuyenNghe) {
        withContext(Dispatchers.IO) {
            cauLuyenNgheDao.updateCauLuyenNghe(cauLuyenNghe)
        }
    }

    // delete cau luyen nghe
    public suspend fun deleteCauLuyenNghe(cauLuyenNghe: CauLuyenNghe) {
        withContext(Dispatchers.IO) {
            cauLuyenNgheDao.deleteCauLuyenNghe(cauLuyenNghe)
        }
    }

    // get cau luyen nghe by id
    public fun getListCauLuyenNgheById(id: Int) : CauLuyenNghe {
        return cauLuyenNgheDao.getListCauLuyenNgheById(id)
    }
}