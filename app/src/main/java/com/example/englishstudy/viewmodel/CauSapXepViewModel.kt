package com.example.englishstudy.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.englishstudy.model.entity.CauSapXep
import com.example.englishstudy.repository.CauSapXepRepository
import kotlinx.coroutines.launch

class CauSapXepViewModel(application: Application) : AndroidViewModel(application) {
    private val cauSapXepRepository = CauSapXepRepository(application.applicationContext)

    // get all cau sap xep
    public suspend fun getListCauSapXep() = cauSapXepRepository.getListCauSapXep()

    // get all cau sap xep by id bo
    public fun getListCauSapXepByIdBo(idBo: Int): LiveData<List<CauSapXep>> {
        return cauSapXepRepository.getListCauSapXepByIdBo(idBo)
    }

    // create cau sap xep
    public fun createCauSapXep(cauSapXep: CauSapXep) {
        viewModelScope.launch { cauSapXepRepository.createCauSapXep(cauSapXep) }
    }

    // get cau sap xep detail
    public suspend fun getCauSapXepDetail(id: Int) = cauSapXepRepository.getCauSapXepDetail(id)

    // update cau sap xep
    public fun updateCauSapXep(cauSapXep: CauSapXep) {
        viewModelScope.launch { cauSapXepRepository.updateCauSapXep(cauSapXep) }
    }

    // delete cau sap xep
    public fun deleteCauSapXep(cauSapXep: CauSapXep) {
        viewModelScope.launch { cauSapXepRepository.deleteCauSapXep(cauSapXep) }
    }

    // get cau sap xep by id
    public fun getCauSapXepById(id: Int) = cauSapXepRepository.getCauSapXepById(id)
}