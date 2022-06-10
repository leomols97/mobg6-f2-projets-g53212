package com.example.lemenestrel.fragmentAndVMs.admin

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

class AdminArtistViewModel : ViewModel() {

    companion object {
        const val text = "Ceci est la section où administrer les données relatives au Ménestrel"
    }

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

    fun displayWelcomeMessage(context: Context): String {
        return text
    }
}
