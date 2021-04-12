package com.school_project.superHeroesApp.ui.cardDetails

import androidx.lifecycle.*
import com.school_project.superHeroesApp.base.BaseViewModel
import com.school_project.superHeroesApp.data.models.superhero.Content
import com.school_project.superHeroesApp.data.repository.Repository
import com.school_project.superHeroesApp.utils.Event
import com.school_project.superHeroesApp.utils.handleNetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.lang.Exception

class CardDetailsViewModel(private val data: Content) : BaseViewModel() {

    private val _cardModel = MutableLiveData(data)
    val cardModel: LiveData<Content> get() = _cardModel

    private val _showConfirmDialog = MutableLiveData<Event<Unit>>()
    val showConfirmDialog: LiveData<Event<Unit>> get() = _showConfirmDialog

    val cardSaved = Repository.getLocalSavedCardsFlow()
        .map { cardIds ->
            if (cardIds.contains(data.id))
                CardSavedState.Saved
            else CardSavedState.NotSaved
        }.asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)

    private val _loginRequired = MutableLiveData<Event<Unit>>()
    val loginRequired: LiveData<Event<Unit>> get() = _loginRequired

    init {
        determineCardSavedState()
    }

    fun buttonClicked() {
        when (cardSaved.value) {
            CardSavedState.Saved -> _showConfirmDialog.postValue(Event(Unit))
            CardSavedState.NotSaved -> saveCard()
            CardSavedState.Unknown -> _loginRequired.postValue(Event(Unit))
        }
    }

    fun deleteConfirmed() {
        deleteCard()
    }

    private fun saveCard() = viewModelScope.launch(Dispatchers.IO) {
        try {
            showLoading()
            Repository.saveCard(data)
        } catch (e: Exception) {
            handleNetworkError(e)
        } finally {
            hideLoading()
        }

    }

    private fun deleteCard() = viewModelScope.launch(Dispatchers.IO) {
        try {
            showLoading()
            Repository.deleteCard(data)
        } catch (e: Exception) {
            handleNetworkError(e)
        } finally {
            hideLoading()
        }
    }

    fun determineCardSavedState() = viewModelScope.launch(Dispatchers.IO) {
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

    override fun onUnauthorized() {
        _loginRequired.postValue(Event(Unit))
    }

    enum class CardSavedState {
        Unknown, Saved, NotSaved
    }

    @Suppress("UNCHECKED_CAST")
    class CardDetailsViewModelFactory(private val data: Content) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CardDetailsViewModel(data) as T
        }
    }


}