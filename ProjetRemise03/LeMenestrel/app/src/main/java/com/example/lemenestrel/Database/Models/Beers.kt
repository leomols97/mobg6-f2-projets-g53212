package com.example.lemenestrel.Database.Models

data class Beers(
    val BeerName: String,
    val Type: String,
    val Breweries: MutableList<String>, // Any have to be replaced by 'Brewery'
    val Alcool: Int,
    val EBC: Int,
    val IBU: Int,
    val Picture: String
)