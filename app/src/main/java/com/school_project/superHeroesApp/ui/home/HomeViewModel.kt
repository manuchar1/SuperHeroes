package com.school_project.superHeroesApp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.school_project.superHeroesApp.R
import com.school_project.superHeroesApp.base.BaseViewModel
import com.school_project.superHeroesApp.base.DialogData
import com.school_project.superHeroesApp.data.models.superhero.Content
import com.school_project.superHeroesApp.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {

    private val _items = MutableLiveData<List<Content>>()
    val items: LiveData<List<Content>> get() = _items

    private val _loadingMore = MutableLiveData(false)
    val loadingMore: LiveData<Boolean> get() = _loadingMore

    private var noMoreData = false

    private var offset = 1

    init {
        loadMore()
    }

    fun onScrollEndReached() {
        if (loadingMore.value == true || noMoreData) return
        loadMore()
    }

    fun onRefresh() {
        offset = 1
        _items.postValue(emptyList())
        loadMore()
    }

    private fun loadMore() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loadingMore.postValue(true)
                val cards = Repository.getRemoteCardsCardsAndStore(
                    offset = offset,
                    pageSize = PAGE_SIZE
                )
                noMoreData = cards.isLast
                offset++
                _items.postValue((_items.value ?: emptyList()) + cards.content)
            } catch (e: Exception) {
                showDialog(DialogData(title = R.string.common_error, message = e.message ?: ""))
            } finally {
                _loadingMore.postValue(false)

            }
        }
    }


    companion object {
        const val PAGE_SIZE = 731
    }
}