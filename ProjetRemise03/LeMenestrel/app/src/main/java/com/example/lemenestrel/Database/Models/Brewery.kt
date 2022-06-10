package com.example.lemenestrel.database.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.net.URL

class Brewery(
    private val breName: MutableLiveData<String>,
    private val beS: MutableLiveData<List<String>>,
    private val pl: MutableLiveData<Place>,
    private val page: MutableLiveData<URL>)
{
    val _breweryName: MutableLiveData<String> = breName
    val breweryName: LiveData<String>
        get() = _breweryName

    val _beers: MutableLiveData<List<String>> = beS
    val beers: LiveData<List<String>>
        get() = _beers

    val _place: MutableLiveData<Place> = pl
    val place: LiveData<Place>
        get() = _place

    val _pageInternet: MutableLiveData<URL> = page
    val pageInternet: LiveData<URL>
        get() = _pageInternet
}
