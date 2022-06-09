package com.example.lemenestrel.database.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.net.URL

class Artist(val aName: MutableLiveData<String>,
             val lName: MutableLiveData<String>,
             val fName: MutableLiveData<String>,
             val page: MutableLiveData<URL>)
{
    val _artistName: MutableLiveData<String> = aName
    val artistName: LiveData<String>
        get() = _artistName

    val _lastName: MutableLiveData<String> = lName
    val lastName: LiveData<String>
        get() = _lastName

    val _firstName: MutableLiveData<String> = fName
    val firstName: LiveData<String>
        get() = _firstName

    val _pageInternet: MutableLiveData<URL> = page
    val pageInternet: LiveData<URL>
        get() = _pageInternet
}
