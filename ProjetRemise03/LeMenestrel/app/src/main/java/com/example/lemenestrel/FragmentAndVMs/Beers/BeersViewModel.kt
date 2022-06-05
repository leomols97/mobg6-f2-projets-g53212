package com.example.lemenestrel.FragmentAndVMs.Beers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BeersViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Voici les bières que nous avons déjà proposées au Ménestrel"
    }
    val text: LiveData<String> = _text
}