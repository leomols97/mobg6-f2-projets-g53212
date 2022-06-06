package com.example.lemenestrel.Database.Models

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Place(val na: MutableLiveData<String>,
            val pl: MutableLiveData<Location>,
             val to: MutableLiveData<String>,
             val co: MutableLiveData<String>)
{
    val _name: MutableLiveData<String> = na
    val name: LiveData<String>
        get() = _name

    val _place: MutableLiveData<Location> = pl
    val place: LiveData<Location>
        get() = _place

    val _town: MutableLiveData<String> = to
    val town: LiveData<String>
        get() = _town

    val _country: MutableLiveData<String> = co
    val country: LiveData<String>
        get() = _country
}
