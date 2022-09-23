package com.example.a0720.UI.Search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.a0720.Repostiry
import com.example.a0720.logic.Model.Place

class SearchViewModel: ViewModel() {

    private val searchLiveData = MutableLiveData<String>()


    val placeList = ArrayList<Place>()


    val placeLiveData = Transformations.switchMap(searchLiveData){ query ->
        Repostiry.searchPlace(query)
    }

    fun searchPlaces(query: String){
        searchLiveData.value = query
    }

    fun savePlace(place: Place) = Repostiry.savePlace(place)

    fun getSavedPlace() = Repostiry.getSavedPlace()

    fun isPlaceSaved() = Repostiry.isPlaceSaved()

}