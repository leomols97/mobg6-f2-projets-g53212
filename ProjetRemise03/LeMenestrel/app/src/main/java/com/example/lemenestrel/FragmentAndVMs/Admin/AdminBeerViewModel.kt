package com.example.lemenestrel.fragmentAndVMs.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.lemenestrel.database.dao.Dao
import com.example.lemenestrel.database.models.Beer

class AdminBeerViewModel(private val dao: Dao) : ViewModel() {

    val beersLiveData = dao.getBeersList()

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
        return dao.getBeerWithName(beerName)
    }
}
