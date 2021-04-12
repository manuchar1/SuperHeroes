package com.school_project.superHeroesApp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.school_project.superHeroesApp.R
import com.school_project.superHeroesApp.base.BaseViewModel
import com.school_project.superHeroesApp.base.DialogData
import com.school_project.superHeroesApp.data.network.NetworkClient
import com.school_project.superHeroesApp.data.repository.Repository
import com.school_project.superHeroesApp.data.storage.DataStore
import com.school_project.superHeroesApp.utils.Event
import com.school_project.superHeroesApp.utils.handleNetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : BaseViewModel() {

    private val _inputError = MutableLiveData<Int>()
    val inputError: LiveData<Int> get() = _inputError

    private val _loginSuccess = MutableLiveData<Event<Unit>>()
    val loginSuccess: LiveData<Event<Unit>> get() = _loginSuccess

    private val _loginFinished = MutableLiveData<Event<Boolean>>()
    val loginFinished: LiveData<Event<Boolean>> get() = _loginFinished




    fun login(username: CharSequence?, password: CharSequence?) = viewModelScope.launch {

        DataStore.authToken = null

        if (username.isNullOrBlank() || password.isNullOrBlank()) {
            _inputError.postValue(R.string.login_enter_username)
            return@launch

        }
        showLoading()

        try {
            val response = withContext(Dispatchers.IO) {

                NetworkClient.userService.login(
                    username = username.toString(),
                    password = password.toString()

                )
            }
            DataStore.authToken = response.accessToken
            _loginSuccess.postValue(Event(Unit))
        } catch (e: Exception) {
            handleNetworkError(e)
            showDialog(
                DialogData(
                title = R.string.common_error,
                message = e.message)
            )

        } finally {
           hideLoading()
        }
    }

    internal fun loginFragmentDestroyed() {
        _loginFinished.postValue(Event(!DataStore.authToken.isNullOrBlank()))
    }

    override fun onUnauthorized() {
        _inputError.postValue(R.string.login_screen_wrong_credentials)
    }

    private suspend fun clearData() {
        DataStore.authToken = null
        Repository.clearSavedCards()
        Repository.invalidateSavedIds()
        Repository.clearProfile()
    }

    fun logOut() = viewModelScope.launch(Dispatchers.IO) {
        clearData()
    }

}