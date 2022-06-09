package com.example.lemenestrel.database.models

data class Beer(
    val Name: String = "",
    val Type: String = "",
    val Alcool: Int = -1,
    val Breweries: MutableList<String> = mutableListOf<String>(), // Any have to be replaced by 'Brewery'
    val EBC: Int = -1,
    val IBU: Int = -1,
    val Picture: String = ""
)