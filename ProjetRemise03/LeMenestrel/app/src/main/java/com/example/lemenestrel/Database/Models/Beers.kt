package com.example.lemenestrel.Database.Models

data class Beers(
    val Name: String = "",
    val Type: String = "",
    val Alcool: Int = -1,
    val Breweries: MutableList<String> = mutableListOf<String>(), // Any have to be replaced by 'Brewery'
    val EBC: Int = -1,
    val IBU: Int = -1,
    val Picture: String = ""
)