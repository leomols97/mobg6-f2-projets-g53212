package com.example.lemenestrel.Database.Dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lemenestrel.Database.Models.Beers
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/* Handles operations on beersLiveData and holds details about it. */
class DataSource() {
    private val beersList = getBeers()
    private val beersLiveData = MutableLiveData(beersList)

    fun getBeers(): List<Beers> {
        val beers: MutableList<Beers> = mutableListOf()
        val ref = FirebaseDatabase.getInstance().getReference("Beers")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val children = dataSnapshot!!.children
                    children.forEach {
                        // This returns every brewery in a single String
                        val breweriesString: Array<String> = arrayOf(it.child("breweries").value.toString())
                        val beerName = it.child("name").value.toString()
                        val beerType = it.child("type").value.toString()
                        val beerAlcool = Integer.parseInt(it.child("alcool").value.toString())
                        val beerEbc = Integer.parseInt(it.child("ebc").value.toString())
                        val beerIbu = Integer.parseInt(it.child("ibu").value.toString())
                        val beerPicture = it.child("picture").value.toString()

                        val beer = Beers(beerName, beerType, beerAlcool, breweriesString.toMutableList(), beerEbc, beerIbu, beerPicture)

                        beers.add(beer)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                throw error.toException()
            }
        })
        return beers
    }
//
//    val ref = FirebaseDatabase.getInstance().getReference("Beers")
//    ref.addListenerForSingleValueEvent(object: ValueEventListener {
//        override fun onDataChange(dataSnapshot: DataSnapshot) {
//            if (dataSnapshot.exists()) {
//                for (dataSnapshotChildren in dataSnapshot.children) {
//                    val beer = dataSnapshotChildren
//                    Log.i(BeersFragment.TAG, beer.getValue().toString())
//                }
//            }
//        }
//        override fun onCancelled(error: DatabaseError) {
//            throw error.toException()
//        }
//    })
//
//    /* Adds flower to liveData and posts value. */
//    fun addFlower(flower: Flower) {
//        val currentList = flowersLiveData.value
//        if (currentList == null) {
//            flowersLiveData.postValue(listOf(flower))
//        } else {
//            val updatedList = currentList.toMutableList()
//            updatedList.add(0, flower)
//            flowersLiveData.postValue(updatedList)
//        }
//    }
//
//    /* Removes flower from liveData and posts value. */
//    fun removeFlower(flower: Flower) {
//        val currentList = flowersLiveData.value
//        if (currentList != null) {
//            val updatedList = currentList.toMutableList()
//            updatedList.remove(flower)
//            flowersLiveData.postValue(updatedList)
//        }
//    }
//
    // Returns beer thanks to his name
    fun getBeerWithName(name: String): Beers? {
        beersLiveData.value?.let { beers ->
            return beers.firstOrNull{ it.Name == name}
        }
        return null
    }

    fun getBeersList(): LiveData<List<Beers>> {
        return beersLiveData
    }
//
//    /* Returns a random flower asset for flowers that are added. */
//    fun getRandomFlowerImageAsset(): Int? {
//        val randomNumber = (initialBeersList.indices).random()
//        return initialBeersList[randomNumber].image
//    }
//
    companion object {
        private var INSTANCE: DataSource? = null

        fun getDataSource(resources: android.content.res.Resources): DataSource {
            return synchronized(DataSource::class) {
                val newInstance = INSTANCE ?: DataSource()
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}