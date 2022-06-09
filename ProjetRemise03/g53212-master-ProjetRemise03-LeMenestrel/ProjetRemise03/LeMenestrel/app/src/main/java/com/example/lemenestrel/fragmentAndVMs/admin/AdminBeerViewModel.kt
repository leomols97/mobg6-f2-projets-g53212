package com.example.lemenestrel.fragmentAndVMs.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.lemenestrel.database.dao.DataSource
import com.example.lemenestrel.database.models.Beer

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
