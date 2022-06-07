package com.example.lemenestrel.FragmentAndVMs.Beers

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lemenestrel.Database.Dao.DataSource
//import com.example.recyclersample.data.DataSource
//import com.example.recyclersample.data.Flower
import kotlin.random.Random

// DataSource just have to be into paramters, or no ModelFactory or companion object will be
// possible to create
class BeersViewModel(val dataSource: DataSource /*application: Application*/) : ViewModel() {

//    val beersLiveData = dataSource.getBeersList()
//
//    /* If the name and description are present, create new Flower and add it to the datasource */
//    fun insertFlower(flowerName: String?, flowerDescription: String?) {
//        if (flowerName == null || flowerDescription == null) {
//            return
//        }
//
//        val image = dataSource.getRandomFlowerImageAsset()
//        val newFlower = Flower(
//            Random.nextLong(),
//            flowerName,
//            image,
//            flowerDescription
//        )
//
//        dataSource.addFlower(newFlower)
//    }
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

/**
 * This is pretty much boiler plate code for a ViewModel Factory.
 *
 * Provides the BeersDatabaseDao and context to the ViewModel.
 */
//class BeersViewModelFactory(
//    private val application: Application
//) : ViewModelProvider.Factory {
////    @Suppress("unchecked_cast")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(BeersViewModel::class.java)) {
//            return BeersViewModel(application) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}