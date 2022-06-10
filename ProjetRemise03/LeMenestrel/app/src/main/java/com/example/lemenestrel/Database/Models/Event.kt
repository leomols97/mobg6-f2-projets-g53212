package com.example.lemenestrel.database.models

import com.google.type.DateTime
import java.net.URL

class Event(
    val Artists: MutableList<String> = mutableListOf<String>(), // 'String' have to be replaced by 'Artist'
    val Breweries: MutableList<String> = mutableListOf<String>(), // 'String' have to be replaced by 'Brewery'
    val Place: String = "", // 'String' have to be replaced by 'Place'
    val DateAndTime: DateTime,
    // Redirects on a website or on a social media page to see the event
    val InternetEvent: String = "" // 'String' has to be replaced by 'URL'
)