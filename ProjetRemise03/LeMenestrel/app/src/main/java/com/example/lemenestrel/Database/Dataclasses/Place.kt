package com.example.lemenestrel.Database.Dataclasses

import android.location.Location

data class Place(
    val place: Location,
    val town: String,
    val country: String
)
