package com.example.lemenestrel.fragmentAndVMs.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.lemenestrel.fragmentAndVMs.admin.FirebaseUserLiveData

class LoginViewModel : ViewModel() {

    // Only for the organisation of the code. Companion a bit like private
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

    /**
     * Gets a fact to display based on the user's set preference of which type of fact they want
     * to see (Android fact or California fact). If there is no logged in user or if the user has
     * not set a preference, defaults to showing Android facts.
     */
    fun displayWelcomeMessage(context: Context): String {
//        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
//        val factTypePreferenceKey = context.getString(R.string.preference_fact_type_key)
//        //val defaultFactType = context.resources.getStringArray(R.array.fact_type)[0]
//        //val funFactType = sharedPreferences.getString(factTypePreferenceKey, defaultFactType)

        return text
    }
}
