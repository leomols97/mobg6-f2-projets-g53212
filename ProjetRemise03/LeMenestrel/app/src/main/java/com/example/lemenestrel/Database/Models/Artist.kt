package com.example.lemenestrel.database.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.net.URL

class Artist(
    val ArtisteName: String = "",
    val LastName: String = "",
    val FirstName: String = "",
    val Page: String = "" // 'String' has to be replaced by 'URL'
)
