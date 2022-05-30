package com.example.lemenestrel.Database.Dataclasses

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Event(val artists: List<Artist>, val breweries: List<Brewery>, val place: Place) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
    val event = Event(artists, breweries, place)

//    database.child("users").child(userId).setValue(user)
}