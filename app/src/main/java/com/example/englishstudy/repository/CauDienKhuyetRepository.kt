package com.example.englishstudy.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.englishstudy.model.database.StudyEnglishDB
import com.example.englishstudy.model.entity.CauDienKhuyet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CauDienKhuyetRepository(val context: Context) {
    private val studyEnglishDB = StudyEnglishDB.getInstance(context)
    private val cauDienKhuyetDao = studyEnglishDB.cauDienKhuyetDao()

    // get all cau dien khuyet
    public suspend fun getListCauDienKhuyet() = cauDienKhuyetDao.getListCauDienKhuyet()

    // get all cau dien khuyet by id bo
    public fun getListCauDienKhuyetByIdBo(idBo: Int) =
        cauDienKhuyetDao.getListCauDienKhuyetByIdBo(idBo)

    // create cau dien khuyet
    public suspend fun createCauDienKhuyet(cauDienKhuyet: CauDienKhuyet) {
        withContext(Dispatchers.IO) {
            cauDienKhuyetDao.insertCauDienKhuyet(cauDienKhuyet)
        }
    }

    // get cau dien khuyet detail
    public suspend fun getCauDienKhuyetDetail(id: Int) = cauDienKhuyetDao.getCauDienKhuyetDetail(id)

    // update cau dien khuyet
    public fun updateCauDienKhuyet(cauDienKhuyet: CauDienKhuyet): Boolean {
        return cauDienKhuyetDao.updateCauDienKhuyet(cauDienKhuyet) > 0
    }

    // delete cau dien khuyet
    public suspend fun deleteCauDienKhuyet(cauDienKhuyet: CauDienKhuyet) {
        withContext(Dispatchers.IO) {
            cauDienKhuyetDao.deleteCauDienKhuyet(cauDienKhuyet)
        }
    }

    // get a specific cau dien khuyet
    public fun getCauDienKhuyetAtPosition(position: Int): LiveData<CauDienKhuyet> {
        return cauDienKhuyetDao.getCauDienKhuyetAtPosition(position)
    }

    // get cau dien khuyet by id
    public fun getCauDienKhuyetById(id: Int): LiveData<CauDienKhuyet> {
        return cauDienKhuyetDao.getCauDienKhuyetById(id)
    }
}