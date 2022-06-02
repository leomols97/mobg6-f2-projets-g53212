package com.example.lemenestrel.Database.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData // make changing the values of the data possible >< LiveData

class Event(val artS: MutableLiveData<List<Artist>>,
            val breS: MutableLiveData<List<Brewery>>,
            val pl: MutableLiveData<Place>)
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
}
