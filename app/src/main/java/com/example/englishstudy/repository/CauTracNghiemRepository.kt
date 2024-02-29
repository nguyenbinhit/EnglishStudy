package com.example.englishstudy.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.englishstudy.model.database.StudyEnglishDB
import com.example.englishstudy.model.entity.CauTracNghiem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CauTracNghiemRepository(val context: Context) {
    private val studyEnglishDB = StudyEnglishDB.getInstance(context)
    private val cauTracNghiemDao = studyEnglishDB.cauTracNghiemDao()

    // get all cau trac nghiem
    public suspend fun getListCauTracNghiem() = cauTracNghiemDao.getListCauTracNghiem()

    // get all cau trac nghiem by id bo
    public fun getListCauTracNghiemByIdBo(idBo: Int) : LiveData<List<CauTracNghiem>> {
        return cauTracNghiemDao.getListCauTracNghiemByIdBo(idBo)
    }

    // create cau trac nghiem
    public suspend fun createCauTracNghiem(cauTracNghiem: CauTracNghiem) {
        withContext(Dispatchers.IO) {
            cauTracNghiemDao.insertCauTracNghiem(cauTracNghiem)
        }
    }

    // get cau trac nghiem detail
    public fun getCauTracNghiemDetail(id: Int) = cauTracNghiemDao.getCauTracNghiemDetail(id)

    // update cau trac nghiem
    public suspend fun updateCauTracNghiem(cauTracNghiem: CauTracNghiem) {
        withContext(Dispatchers.IO) {
            cauTracNghiemDao.updateCauTracNghiem(cauTracNghiem)
        }
    }

    // delete cau trac nghiem
    public suspend fun deleteCauTracNghiem(cauTracNghiem: CauTracNghiem) {
        withContext(Dispatchers.IO) {
            cauTracNghiemDao.deleteCauTracNghiem(cauTracNghiem)
        }
    }
}