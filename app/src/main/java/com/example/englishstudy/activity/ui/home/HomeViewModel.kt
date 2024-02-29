package com.example.englishstudy.activity.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private val _text = MutableLiveData<String>()

    val text: MutableLiveData<String>
        get() = _text

    init {
        _text.value = "This is home fragment"
    }
}