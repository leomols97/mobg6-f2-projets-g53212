package com.example.projetremise01.connect

import android.app.Application
import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projetremise01.database.MailDatabaseDao
import com.example.projetremise01.database.MailInfos
import java.time.LocalDateTime

class ConnectViewModel(
    val database: MailDatabaseDao,
    application: Application) : AndroidViewModel(application) {

    private val _mailAdress = MutableLiveData<String>()
    val mailAdress: LiveData<String>
        get() = _mailAdress

    private val _statusMessage = MutableLiveData<Event<String>>()

    val message : LiveData<Event<String>>
        get() = _statusMessage

    init {
        _mailAdress.value = ""
    }

    fun onConnectButton() {
        if (!onCheck(_mailAdress.toString())) {
            _statusMessage.value = Event("L'adresse mail n'est pas correctement entrée")
        }
        // Hides the keybooard
//        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun onCheck(mail: String?): Boolean {
        if(!TextUtils.isEmpty(mail)
            && database.get(mail.toString()) != null)
            database.update(LocalDateTime.now())
        if(!TextUtils.isEmpty(mail)
            && database.get(mail.toString()) == null)
            database.insert(MailInfos(LocalDateTime.now(), mail.toString()))
        return !TextUtils.isEmpty(mail) && Patterns.EMAIL_ADDRESS.matcher(mail.toString()).matches()
    }
}

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}

