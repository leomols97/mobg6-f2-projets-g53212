package com.example.lemenestrel.FragmentAndVMs.Admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.lemenestrel.Database.Dao.DataSource
import com.example.lemenestrel.Database.Models.Beer

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

    fun getBeerWithName(beerName: String): Beer? {
        return dataSource.getBeerWithName(beerName)
    }
}
