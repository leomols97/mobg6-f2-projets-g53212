package com.example.lemenestrel.Database.Models

data class Beers(
    val BeerName: String = "",
    val Type: String = "",
    val Breweries: MutableList<String> = mutableListOf<String>(), // Any have to be replaced by 'Brewery'
    val Alcool: Int = 0,
    val EBC: Int = 0,
    val IBU: Int = 0,
    val Picture: String = ""
)