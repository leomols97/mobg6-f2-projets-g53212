package com.example.lemenestrel.Database.Models

import androidx.lifecycle.MutableLiveData

data class Beers(
    val beName: String,
    val ty: String,
    val breS: MutableList<String>, // Any have to be replaced by 'Brewery'
    val alc: Int,
    val eb: Int,
    val ib: Int
)
