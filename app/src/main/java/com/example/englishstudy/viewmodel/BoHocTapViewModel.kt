package com.example.englishstudy.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.englishstudy.model.database.StudyEnglishDB
import com.example.englishstudy.model.entity.BoHocTap
import com.example.englishstudy.repository.BoHocTapRepository
import kotlinx.coroutines.launch

class BoHocTapViewModel(application: Application) : AndroidViewModel(application) {
    private val boHocTapRepository = BoHocTapRepository(application.applicationContext)

    // get all bo hoc tap
    public fun getListBoHocTap(): LiveData<List<BoHocTap>> {
        return boHocTapRepository.getListBoHocTap()
    }

    // create bo hoc tap
    public fun createBoHocTap(boHocTap: BoHocTap) {
        viewModelScope.launch { boHocTapRepository.createBoHocTap(boHocTap) }
    }

    // get bo hoc tap detail
    public fun getBoHocTapDetail(id: Int): BoHocTap {
        return boHocTapRepository.getBoHocTapDetail(id)
    }

    // update bo hoc tap
    public fun updateBoHocTap(id: Int, stt: Int, ten: String) {
        val boHocTap = BoHocTap(stt, ten).apply { this.id = id }
        viewModelScope.launch { boHocTapRepository.updateBoHocTap(boHocTap) }
    }

    // delete bo hoc tap
    public fun deleteBoHocTap(boHocTap: BoHocTap) {
        viewModelScope.launch { boHocTapRepository.deleteBoHocTap(boHocTap) }
    }

    // get a specific bo hoc tap
    public fun getBoHocTapAtPosition(position: Int): LiveData<BoHocTap> {
        return boHocTapRepository.getBoHocTapAtPosition(position)
    }

    // get max stt bo hoc tap
    public fun getMaxSTTBoHocTap(): Int {
        return boHocTapRepository.getMaxSTTBoHocTap()
    }
}