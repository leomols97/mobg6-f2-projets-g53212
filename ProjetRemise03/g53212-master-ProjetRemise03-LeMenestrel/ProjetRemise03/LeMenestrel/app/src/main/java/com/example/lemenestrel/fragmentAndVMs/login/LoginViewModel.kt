package com.example.lemenestrel.fragmentAndVMs.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.lemenestrel.fragmentAndVMs.admin.FirebaseUserLiveData

class LoginViewModel : ViewModel() {

    // Only for the organisation of the code. Companion a bit like private
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
//        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
//        val factTypePreferenceKey = context.getString(R.string.preference_fact_type_key)
//        //val defaultFactType = context.resources.getStringArray(R.array.fact_type)[0]
//        //val funFactType = sharedPreferences.getString(factTypePreferenceKey, defaultFactType)

        return androidFacts
    }

//    private fun launchSignInFlow() {
//        // Give users the option to sign in / register with their email or Google account. If users
//        // choose to register with their email, they will need to create a password as well.
//        val providers = arrayListOf(
//            AuthUI.IdpConfig.EmailBuilder().build(), AuthUI.IdpConfig.GoogleBuilder().build()
//        )
//
//        // Create and launch sign-in intent. We listen to the response of this activity with the
//        // SIGN_IN_RESULT_CODE code.
//        startActivityForResult(
//            AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(
//                providers
//            ).build(), LoginFragment.SIGN_IN_RESULT_CODE
//        )
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == LoginFragment.SIGN_IN_RESULT_CODE) {
//            val response = IdpResponse.fromResultIntent(data)
//            if (resultCode == Activity.RESULT_OK) {
//                // Successfully signed in user.
//                Log.i(
//                    LoginFragment.TAG,
//                    "Successfully signed in user " +
//                            "${FirebaseAuth.getInstance().currentUser?.displayName}!"
//                )
//            } else {
//                // Sign in failed. If response is null the user canceled the sign-in flow using
//                // the back button. Otherwise check response.getError().getErrorCode() and handle
//                // the error.
//                Log.i(LoginFragment.TAG, "Sign in unsuccessful ${response?.error?.errorCode}")
//            }
//        }
//    }
}
