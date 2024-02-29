package com.example.englishstudy.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishstudy.model.entity.TuVung
import com.example.englishstudy.repository.TuVungRepository
import kotlinx.coroutines.launch

class TuVungViewMModel(application: Application) : AndroidViewModel(application){
    private val tuVungRepository = TuVungRepository(application.applicationContext)

    // get all tu vung
    public suspend fun getListTuVung() = tuVungRepository.getListTuVung()

    // get all tu vung by id bo
    public fun getListTuVungByIdBo(idBo: Int) = tuVungRepository.getListTuVungByIdBo(idBo)

    // create tu vung
    public fun createTuVung(tuVung: TuVung) {
        viewModelScope.launch { tuVungRepository.createTuVung(tuVung) }
    }

    // get tu vung detail
    public fun getTuVungDetail(id: Int) = tuVungRepository.getTuVungDetail(id)

    // update tu vung
    public fun updateTuVung(tuVung: TuVung) {
        viewModelScope.launch { tuVungRepository.updateTuVung(tuVung) }
    }

    // delete tu vung
    public fun deleteTuVung(tuVung: TuVung) {
        viewModelScope.launch { tuVungRepository.deleteTuVung(tuVung) }
    }

    // get all tu vung by id
    public fun getListTuVungById(id: Int) = tuVungRepository.getListTuVungById(id)
}