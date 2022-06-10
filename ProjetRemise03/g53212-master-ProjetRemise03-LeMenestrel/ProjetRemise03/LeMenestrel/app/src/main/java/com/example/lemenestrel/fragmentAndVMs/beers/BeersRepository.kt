package com.example.lemenestrel.fragmentAndVMs.beers

import androidx.lifecycle.MutableLiveData
import com.example.lemenestrel.database.models.Beer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class BeersRepository {

    private val database = Firebase.database
    private val databaseReference = database.getReference("Beers")

    fun fetchNewsFeed(liveData: MutableLiveData<List<Beer>>) {
        databaseReference
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val beers: List<Beer> = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(Beer::class.java)!!.copy(Name = dataSnapshot.key!!)
                    }

                    liveData.postValue(beers)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Nothing to do
                }
            })
    }
}