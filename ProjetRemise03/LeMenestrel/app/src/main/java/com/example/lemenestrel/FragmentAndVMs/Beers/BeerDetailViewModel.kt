package com.example.lemenestrel.fragmentAndVMs.beers

//class BeerDetailViewModel(private val beertKey: String = "", private val datasource: Dao) : ViewModel() {
//
//    // Queries datasource to returns a Beer that corresponds to a name
//    fun getBeerWithName(name: String) : Beer? {
//        return datasource.getBeerWithName(name)
//    }
//}
//
//class BeerDetailViewModelFactory(/*private val context: Context*/) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(BeerDetailViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return BeerDetailViewModel(
//                datasource = Dao.getDao(/*context.resources*/)
//            ) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}