package com.example.lemenestrel.database.models

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Place(
    private val na: MutableLiveData<String>,
    private val pl: MutableLiveData<Location>,
    val to: MutableLiveData<String>,
    private val co: MutableLiveData<String>)
{
    private val _name: MutableLiveData<String> = na
    val name: LiveData<String>
        get() = _name

    private val _place: MutableLiveData<Location> = pl
    val place: LiveData<Location>
        get() = _place

    private val _town: MutableLiveData<String> = to
    val town: LiveData<String>
        get() = _town

    private val _country: MutableLiveData<String> = co
    val country: LiveData<String>
        get() = _country
}
