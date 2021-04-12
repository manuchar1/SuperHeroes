package com.school_project.superHeroesApp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_project.superHeroesApp.data.models.superhero.Content
import com.school_project.superHeroesApp.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class SearchViewModel : ViewModel() {

    private val _cards = MutableLiveData<List<Content>>()
    val cards: LiveData<List<Content>> get() = _cards

    val message = MutableLiveData<String>()


    fun onSearchTextChange(string: CharSequence?) {
        if (string.isNullOrEmpty()) _cards.postValue(emptyList())
        if (string?.length ?: 0 < 3) return
        viewModelScope.launch {
            launch(Dispatchers.IO) {
                try {
                    val cards = Repository.getCardsByName(string = string.toString())
                    _cards.postValue(cards)
                    message.postValue("Count ${cards.size}")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

}