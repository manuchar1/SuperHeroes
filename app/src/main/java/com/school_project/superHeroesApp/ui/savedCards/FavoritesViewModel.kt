package com.school_project.superHeroesApp.ui.savedCards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.school_project.superHeroesApp.base.BaseViewModel
import com.school_project.superHeroesApp.data.models.superhero.Content
import com.school_project.superHeroesApp.data.repository.Repository
import com.school_project.superHeroesApp.utils.Event
import com.school_project.superHeroesApp.utils.handleNetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FavoritesViewModel : BaseViewModel() {

    private val _requestLogin = MutableLiveData<Event<Unit>>()
    val requestLogin: LiveData<Event<Unit>> get() = _requestLogin

    val userCards: LiveData<List<Content>> =
        Repository.getLocalSavedCardsFlow()
            .map { list ->
                list.map { id ->
                    var card: Content? = null
                    card = Repository.getLocalCardById(id)
                    if (card == null) {
                        showLoading()
                        card = Repository.getRemoteCardById(id)
                        hideLoading()
                    }
                    card
                }
            }.catch { error -> handleNetworkError(error) }
            .flowOn(Dispatchers.IO)
            .asLiveData(viewModelScope.coroutineContext)

    init {
        getSavedCards()
    }

    fun getSavedCards() = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (!Repository.checkSavedIdsValidity()) {
                showLoading()
                Repository.updateRemoteSavedCards()
            }
        } catch (e: Exception) {
            handleNetworkError(e)
        } finally {
            hideLoading()
        }
    }

    fun refresh() = viewModelScope.launch(Dispatchers.IO) {
        try {
            showLoading()
            Repository.updateRemoteSavedCards()
        } catch (e: Exception) {
            handleNetworkError(e)
        } finally {
            hideLoading()
        }
        getSavedCards()
    }

    override fun onUnauthorized() {
        _requestLogin.postValue(Event(Unit))
    }



}