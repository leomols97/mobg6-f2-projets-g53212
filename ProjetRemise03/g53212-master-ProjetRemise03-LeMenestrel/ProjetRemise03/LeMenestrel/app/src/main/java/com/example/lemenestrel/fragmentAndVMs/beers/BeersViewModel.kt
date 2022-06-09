package com.example.lemenestrel.fragmentAndVMs.beers

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lemenestrel.database.dao.Dao
import com.example.lemenestrel.database.models.Beer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_beers.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

// Dao just have to be into paramters, or no ModelFactory or companion object will be
// possible to create
class BeersViewModel(val dao: Dao) : ViewModel() {

    val beersList = dao.getBeersList()

    // References the Firebase folder with all the beer pictures
    val picturesReference = FirebaseStorage.getInstance().reference

    private val _navigateToBeerDetail = MutableLiveData<String>()
    val navigateToBeerDetail
        get() = _navigateToBeerDetail
    fun onBeerClicked(beerName: String) {
        _navigateToBeerDetail.value = beerName
    }
    fun onBeerDetailNavigated() {
        _navigateToBeerDetail.value = null
    }
}

class BeersViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BeersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BeersViewModel(
                dao = Dao.getDao()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}