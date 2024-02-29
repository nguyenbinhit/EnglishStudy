package com.example.englishstudy.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.englishstudy.model.entity.CauTracNghiem
import com.example.englishstudy.repository.CauTracNghiemRepository
import kotlinx.coroutines.launch

class CauTracNghiemViewModel(application: Application) : AndroidViewModel(application) {
    private val cauTracNghiemRepository = CauTracNghiemRepository(application.applicationContext)

    // get all cau trac nghiem
    public suspend fun getListCauTracNghiem() = cauTracNghiemRepository.getListCauTracNghiem()

    // get all cau trac nghiem by id bo
    public fun getListCauTracNghiemByIdBo(idBo: Int) : LiveData<List<CauTracNghiem>> {
        return cauTracNghiemRepository.getListCauTracNghiemByIdBo(idBo)
    }


    // create cau trac nghiem
    public fun createCauTracNghiem(cauTracNghiem: CauTracNghiem) {
        viewModelScope.launch { cauTracNghiemRepository.createCauTracNghiem(cauTracNghiem) }
    }

    // get cau trac nghiem detail
    public fun getCauTracNghiemDetail(id: Int) =
        cauTracNghiemRepository.getCauTracNghiemDetail(id)

    // update cau trac nghiem
    public fun updateCauTracNghiem(cauTracNghiem: CauTracNghiem) {
        viewModelScope.launch { cauTracNghiemRepository.updateCauTracNghiem(cauTracNghiem) }
    }

    // delete cau trac nghiem
    public fun deleteCauTracNghiem(cauTracNghiem: CauTracNghiem) {
        viewModelScope.launch { cauTracNghiemRepository.deleteCauTracNghiem(cauTracNghiem) }
    }
}