package com.example.lemenestrel.fragmentAndVMs.breweries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BreweriesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is breweries Fragment"
    }
    val text: LiveData<String> = _text
}