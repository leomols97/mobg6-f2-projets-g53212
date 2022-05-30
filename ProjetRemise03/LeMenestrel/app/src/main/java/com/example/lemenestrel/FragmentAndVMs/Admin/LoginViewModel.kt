package com.example.lemenestrel.FragmentAndVMs.Admin

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.preference.PreferenceManager
import com.example.lemenestrel.R

class LoginViewModel : ViewModel() {

    companion object {
        val androidFacts = "Ceci est la section où administrer les données relatives au Ménestrel"
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

    /**
     * Gets a fact to display based on the user's set preference of which type of fact they want
     * to see (Android fact or California fact). If there is no logged in user or if the user has
     * not set a preference, defaults to showing Android facts.
     */
    fun getFactToDisplay(context: Context): String {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val factTypePreferenceKey = context.getString(R.string.preference_fact_type_key)
        //val defaultFactType = context.resources.getStringArray(R.array.fact_type)[0]
        //val funFactType = sharedPreferences.getString(factTypePreferenceKey, defaultFactType)

        return androidFacts
    }
}
