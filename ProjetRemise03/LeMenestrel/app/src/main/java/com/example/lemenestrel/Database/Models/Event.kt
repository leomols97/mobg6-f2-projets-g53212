package com.example.lemenestrel.Database.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData // make changing the values of the data possible >< LiveData
import com.google.type.DateTime
import java.time.chrono.ChronoLocalDateTime

class Event(val artS: MutableLiveData<List<Artist>>,
            val breS: MutableLiveData<List<Brewery>>,
            val pl: MutableLiveData<Place>,
            val dT: MutableLiveData<DateTime>)
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
