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

    private fun getBeers(): List<Beers> {
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