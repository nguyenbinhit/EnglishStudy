package com.example.englishstudy.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.englishstudy.model.entity.CauLuyenNghe
import com.example.englishstudy.repository.CauLuyenNgheRepository
import kotlinx.coroutines.launch

class CauLuyenNgheViewModel(application: Application) : AndroidViewModel(application) {
    private val cauLuyenNgheRepository = CauLuyenNgheRepository(application.applicationContext)

    // get all cau luyen nghe
    public suspend fun getListCauLuyenNghe() = cauLuyenNgheRepository.getListCauLuyenNghe()

    // get all cau luyen nghe by id bo
    public fun getListCauLuyenNgheByIdBo(idBo: Int): LiveData<List<CauLuyenNghe>> {
        return cauLuyenNgheRepository.getListCauLuyenNgheByIdBo(idBo)
    }

    // create cau luyen nghe
    public fun createCauLuyenNghe(cauLuyenNghe: CauLuyenNghe) {
        viewModelScope.launch { cauLuyenNgheRepository.createCauLuyenNghe(cauLuyenNghe) }
    }

    // get cau luyen nghe detail
    public suspend fun getCauLuyenNgheDetail(id: Int) =
        cauLuyenNgheRepository.getCauLuyenNgheDetail(id)

    // update cau luyen nghe
    public fun updateCauLuyenNghe(cauLuyenNghe: CauLuyenNghe) {
        viewModelScope.launch { cauLuyenNgheRepository.updateCauLuyenNghe(cauLuyenNghe) }
    }

    // delete cau luyen nghe
    public fun deleteCauLuyenNghe(cauLuyenNghe: CauLuyenNghe) {
        viewModelScope.launch { cauLuyenNgheRepository.deleteCauLuyenNghe(cauLuyenNghe) }
    }

    // get cau luyen nghe by id
    public fun getListCauLuyenNgheById(id: Int): CauLuyenNghe {
        return cauLuyenNgheRepository.getListCauLuyenNgheById(id)
    }

    interface CauLuyenNgheCallback {
        fun onCauLuyenNgheLoaded(cauLuyenNghe: CauLuyenNghe)
    }

    fun getCauLuyenNgheById(id: Int, callback: CauLuyenNgheCallback) {
        viewModelScope.launch {
            val cauLuyenNghe = cauLuyenNgheRepository.getListCauLuyenNgheById(id)
            callback.onCauLuyenNgheLoaded(cauLuyenNghe)
        }
    }
}