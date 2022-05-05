package com.example.projetremise01.connect

import android.app.Application
import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projetremise01.database.MailDatabase
import com.example.projetremise01.database.MailDatabase.Companion.getInstance
import com.example.projetremise01.database.MailDatabaseDao
import com.example.projetremise01.database.MailInfos
import com.ktvipin.roominspector.query.QueryRunner.init
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class ConnectViewModel (
    /*application: Application*/) : ViewModel() {

    private lateinit var database: MailDatabaseDao
    private val _mailAdress = MutableLiveData<String>()

    val mailAdress: LiveData<String>
        get() = _mailAdress

    private val _statusMessage = MutableLiveData<Event<String>>()

    val message : LiveData<Event<String>>
        get() = _statusMessage

    init {
        _mailAdress.value = ""
    }

//    fun onConnectButton() {
//        if (!isValidEmail(_mailAdress.toString())) {
//            _statusMessage.value = Event("L'adresse mail n'est pas correctement entrée")
//        }
//        // Hides the keybooard
////        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
////        imm.hideSoftInputFromWindow(view.windowToken, 0)
//    }

    fun insertEmail(mailAdress: String)
    {
        database.insert(MailInfos(LocalDateTime.now(), mailAdress))
    }

//    private fun onCheck(mail: String?): Boolean {
//        if(!TextUtils.isEmpty(mail)
//            && database.get(mail.toString()) != null)
//            database.update(LocalDateTime.now())
//        if(!TextUtils.isEmpty(mail)
//            && database.get(mail.toString()) == null)
//            database.insert(MailInfos(LocalDateTime.now(), mail.toString()))
//        return !TextUtils.isEmpty(mail) && Patterns.EMAIL_ADDRESS.matcher(mail.toString()).matches()
//    }

    fun initializeDatabase(context: Context)
    {
        database = MailDatabase.getInstance(context).mailDatabaseDao

        _mailAdress
    }
}

open class Event<out T>(private val content: T) {

    private var hasBeenHandled = false
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

