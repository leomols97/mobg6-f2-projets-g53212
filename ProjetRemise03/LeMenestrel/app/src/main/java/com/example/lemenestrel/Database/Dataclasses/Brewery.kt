package com.example.lemenestrel.Database.Dataclasses

import java.net.URL

data class Brewery (
    val name: String,
    val beers: List<Beer>,
    val place: Place,
    val page: URL
)
