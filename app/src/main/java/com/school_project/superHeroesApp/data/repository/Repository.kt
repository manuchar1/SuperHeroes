package com.school_project.superHeroesApp.data.repository

import com.school_project.superHeroesApp.data.models.superhero.Content
import com.school_project.superHeroesApp.data.models.superhero.PaginatedData
import com.school_project.superHeroesApp.data.models.user.UserRegistrationRequest
import com.school_project.superHeroesApp.data.network.NetworkClient
import com.school_project.superHeroesApp.data.storage.DataStore
import com.school_project.superHeroesApp.data.storage.db.entities.SavedCardIdEntity
import kotlinx.coroutines.flow.map

object Repository {

    suspend fun getLocalCardById(id: Int): Content? {
        return DataStore.db.getCardDAO().getById(id)
    }

    suspend fun getRemoteCardById(id: Int): Content {
        return NetworkClient.heroService.getCardBydId(id).data
            .also {
                DataStore.db.getCardDAO().insert(it)
            }
    }

    suspend fun getRemoteCardsCardsAndStore(
        offset: Int,
        pageSize: Int
    ): PaginatedData<List<Content>> {
        val cards = NetworkClient.heroService.getCards(
            offset = offset,
            pageSize = pageSize
        )
        DataStore.db.getCardDAO().insert(cards.content)
        return cards
    }

    fun getLocalSavedCardsFlow() =
        DataStore.db.getSavedCardsDao().getSavedCardFlow()
            .map { list -> list.map { it.id } }

    suspend fun clearSavedCards() =
        DataStore.db.getSavedCardsDao().deleteAll()


    suspend fun updateRemoteSavedCards(): List<Int> {
        val ids = NetworkClient.userService.getUserCharacter().map { it }
        DataStore.db.getSavedCardsDao().insert(ids = ids.map { SavedCardIdEntity(it) })
        DataStore.lastTimeSavedCardsFetched = System.currentTimeMillis()
        return ids
    }

    suspend fun loginAndSetToken(username: String, password: String) {
        NetworkClient.userService.login(
            username = username,
            password = password
        ).apply {
            DataStore.authToken = accessToken
        }
    }

    suspend fun registerAndLogin(
        name: String,
        userName: String,
        password: String
    ) {
        NetworkClient.userService.register(
            UserRegistrationRequest(
                name = name,
                userName = userName,
                password = password
            )
        )
        NetworkClient.userService.login(
            username = userName,
            password = password
        ).accessToken.apply {
            DataStore.authToken = this
        }
    }

    suspend fun saveCard(card: Content) {
        NetworkClient.userService.saveUserCharacter(card.id)
        DataStore.db.getSavedCardsDao().insert(SavedCardIdEntity(card.id))
    }

    suspend fun deleteCard(card: Content) {
        NetworkClient.userService.deleteUserCharacter(card.id)
        DataStore.db.getSavedCardsDao().delete(SavedCardIdEntity(card.id))
    }

    suspend fun checkSavedIdsValidity(): Boolean =
        System.currentTimeMillis() - DataStore.lastTimeSavedCardsFetched < 60 * 60 * 1000

    suspend fun invalidateSavedIds() {
        DataStore.lastTimeSavedCardsFetched = 0
    }

    suspend fun getRemoteAndSaveProfile() {
        DataStore.db.getUserProfileDAO().insert(
            NetworkClient.userService.getUser()
        )
    }

    suspend fun clearProfile() {
        DataStore.db.getUserProfileDAO().delete()
    }

    suspend fun getCardsByName(string: String) = NetworkClient.heroService.searchCards(
        "$string"
    ).content
}