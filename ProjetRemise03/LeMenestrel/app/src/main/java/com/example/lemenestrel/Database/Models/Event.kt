package com.example.lemenestrel.database.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData // make changing the values of the data possible >< LiveData
import com.google.type.DateTime

class Event(
    private val artS: MutableLiveData<List<Artist>>,
    private val breS: MutableLiveData<List<Brewery>>,
    private val pl: MutableLiveData<Place>,
    private val dT: MutableLiveData<DateTime>)
{
    val _artist: MutableLiveData<List<Artist>> = artS
    val artist: LiveData<List<Artist>>
        get() = _artist

    val _breweries: MutableLiveData<List<Brewery>> = breS
    val breweries: LiveData<List<Brewery>>
        get() = _breweries

    val _place: MutableLiveData<Place> = pl
    val place: LiveData<Place>
        get() = _place

    val _dateTime: MutableLiveData<DateTime> = dT
    val dataTime: LiveData<DateTime>
        get() = _dateTime
}
