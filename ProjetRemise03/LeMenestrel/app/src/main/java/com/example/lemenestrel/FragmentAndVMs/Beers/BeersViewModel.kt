package com.example.lemenestrel.FragmentAndVMs.Beers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BeersViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is beers Fragment"
    }
    val text: LiveData<String> = _text
}