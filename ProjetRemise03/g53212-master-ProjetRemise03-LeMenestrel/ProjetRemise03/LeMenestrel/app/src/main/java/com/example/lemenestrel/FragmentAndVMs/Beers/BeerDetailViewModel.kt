package com.example.lemenestrel.FragmentAndVMs.Beers

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lemenestrel.Database.Dao.DataSource
import com.example.lemenestrel.Database.Models.Beer

class BeerDetailViewModel(private val datasource: DataSource) : ViewModel() {

    // Queries datasource to returns a Beer that corresponds to a name
    fun getBeerWithName(name: String) : Beer? {
        return datasource.getBeerWithName(name)
    }
}

class BeerDetailViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BeerDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BeerDetailViewModel(
                datasource = DataSource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}