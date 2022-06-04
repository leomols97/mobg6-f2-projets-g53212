package com.example.lemenestrel.Database.Models

data class Beers(
    val beName: String ?= null,
    val ty: String ?= null,
    val breS: MutableList<String> ?= null, // Any have to be replaced by 'Brewery'
    val alc: Int ?= null,
    val eb: Int ?= null,
    val ib: Int ?= null
)
