package com.example.lemenestrel.FragmentAndVMs.Admin

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.preference.PreferenceManager
import com.example.lemenestrel.Database.Dao.DataSource
import com.example.lemenestrel.Database.Models.Beers
import com.example.lemenestrel.R

class AdminBeerViewModel(val dataSource: DataSource) : ViewModel() {

    companion object {
        val androidFacts = "Ceci est la section où administrer les données relatives au Ménestrel"
    }

    val beersLiveData = dataSource.getBeersList()

    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

    val authenticationState = FirebaseUserLiveData().map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }

    fun getBeerWithName(beerName: String): Beers? {
        return dataSource.getBeerWithName(beerName)
    }
}
