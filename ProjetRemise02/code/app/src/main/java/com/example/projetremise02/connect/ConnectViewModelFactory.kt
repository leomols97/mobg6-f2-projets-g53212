package com.example.projetremise02.connect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projetremise02.database.MailDatabaseDao

/**
 * This is pretty much boiler plate code for a ViewModel Factory.
 *
 * Provides the SleepDatabaseDao and context to the ViewModel.
 */
class ConnectViewModelFactory(
        private val mailAdress: String,
        private val dataSource: MailDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConnectViewModel::class.java)) {
            return ConnectViewModel(dataSource, mailAdress) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

