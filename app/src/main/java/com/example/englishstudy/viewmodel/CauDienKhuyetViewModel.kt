package com.example.englishstudy.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.englishstudy.model.entity.CauDienKhuyet
import com.example.englishstudy.repository.CauDienKhuyetRepository
import kotlinx.coroutines.launch

class CauDienKhuyetViewModel(application: Application) : AndroidViewModel(application) {
    private val cauDienKhuyetRepository = CauDienKhuyetRepository(application.applicationContext)

    // get all cau dien khuyet
    public suspend fun getListCauDienKhuyet() = cauDienKhuyetRepository.getListCauDienKhuyet()

    // get all cau dien khuyet by id bo
    public fun getListCauDienKhuyetByIdBo(idBo: Int) =
        cauDienKhuyetRepository.getListCauDienKhuyetByIdBo(idBo)

    // create cau dien khuyet
    public fun createCauDienKhuyet(cauDienKhuyet: CauDienKhuyet) {
        viewModelScope.launch { cauDienKhuyetRepository.createCauDienKhuyet(cauDienKhuyet) }
    }

    // get cau dien khuyet detail
    public suspend fun getCauDienKhuyetDetail(id: Int) =
        cauDienKhuyetRepository.getCauDienKhuyetDetail(id)

    // update cau dien khuyet
    public fun updateCauDienKhuyet(cauDienKhuyet: CauDienKhuyet): Boolean {
        return cauDienKhuyetRepository.updateCauDienKhuyet(cauDienKhuyet)
    }

    // delete cau dien khuyet
    public fun deleteCauDienKhuyet(cauDienKhuyet: CauDienKhuyet) {
        viewModelScope.launch { cauDienKhuyetRepository.deleteCauDienKhuyet(cauDienKhuyet) }
    }

    // get a specific cau dien khuyet
    public fun getCauDienKhuyetAtPosition(position: Int): LiveData<CauDienKhuyet> {
        return cauDienKhuyetRepository.getCauDienKhuyetAtPosition(position)
    }

    // get cau dien khuyet by id
    public fun getCauDienKhuyetById(id: Int): LiveData<CauDienKhuyet> {
        return cauDienKhuyetRepository.getCauDienKhuyetById(id)
    }
}