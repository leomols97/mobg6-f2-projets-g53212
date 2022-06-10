package com.example.lemenestrel.database.models

import java.net.URL

class Brewery(val BreweryName: String = "",
    val Beers: MutableList<String> = mutableListOf<String>(), // 'String' have to be replaced by 'Beers'
    val Place: String = "", // 'String' have to be replaced by 'Place'
    val Page: URL // 'String' has to be replaced by 'URL'
    )