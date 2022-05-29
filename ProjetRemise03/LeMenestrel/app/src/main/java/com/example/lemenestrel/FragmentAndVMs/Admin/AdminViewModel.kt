package com.example.lemenestrel.FragmentAndVMs.Admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AdminViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is admin Fragment"
    }
    val text: LiveData<String> = _text
}