package com.example.lemenestrel.FragmentAndVMs.Beers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lemenestrel.Database.Dao.DataSource
import kotlin.random.Random

// DataSource just have to be into paramters, or no ModelFactory or companion object will be
// possible to create
class BeersViewModel(val dataSource: DataSource) : ViewModel() {

    val beersLiveData = dataSource.getBeersList()
}

//class BeersViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(BeersViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return BeersViewModel(
////                dataSource = DataSource.getDataSource(context.resources)
//            ) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}