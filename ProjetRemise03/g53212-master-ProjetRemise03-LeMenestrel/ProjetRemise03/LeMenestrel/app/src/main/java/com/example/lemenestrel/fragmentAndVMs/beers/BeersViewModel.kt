package com.example.lemenestrel.fragmentAndVMs.beers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lemenestrel.database.models.Beer
import com.google.firebase.storage.FirebaseStorage

// Dao just have to be into paramters, or no ModelFactory or companion object will be
// possible to create
class BeersViewModel(/*val dao: Dao*/) : ViewModel() {

//    val beersList = dao.getBeersList()

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

    private val repository = BeersRepository()

    private val _beersLiveData = MutableLiveData<List<Beer>>()
//    val beersLiveData: LiveData<List<Beer>> = _beersLiveData

    fun fetchBeersFeed() {
        repository.fetchNewsFeed(_beersLiveData)
    }
}

class BeersViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BeersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BeersViewModel(
//                dao = Dao.getDao()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}