package com.example.lemenestrel.Database.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Beer(val beName: MutableLiveData<String>,
           val ty: MutableLiveData<String>,
           val breS: MutableLiveData<Array<String>>,
           val alc: MutableLiveData<Int>,
           val eb: MutableLiveData<Int>,
           val ib: MutableLiveData<Int>)
{
    val _beerName: MutableLiveData<String> = beName
    val beerName: LiveData<String>
        get() = _beerName

    val _type: MutableLiveData<String> = ty
    val type: LiveData<String>
        get() = _type

    val _breweries: MutableLiveData<Array<String>> = breS
    val breweries: LiveData<Array<String>>
        get() = _breweries

    val _alcool: MutableLiveData<Int> = alc
    val alcool: LiveData<Int>
        get() = _alcool

    val _ebc: MutableLiveData<Int> = eb
    val ebc: LiveData<Int>
        get() = _ebc

    val _ibu: MutableLiveData<Int> = ib
    val ibu: LiveData<Int>
        get() = _ibu
}


