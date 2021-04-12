package com.school_project.superHeroesApp.data.network

import com.school_project.superHeroesApp.data.models.user.UserProfile
import com.school_project.superHeroesApp.data.models.user.UserRegistrationRequest
import com.school_project.superHeroesApp.data.models.user.UserSession
import retrofit2.http.*

interface UserService {

    @POST("/auth/login")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): UserSession

    @POST("/auth/register")
    suspend fun register(@Body request: UserRegistrationRequest)

    @GET("/auth/user")
    suspend fun getUser(): UserProfile


    @GET("/user/comics/get-my-characters")
    suspend fun getUserCharacter(): List<Int>

    @POST("/user/comics/save-character")
    suspend fun saveUserCharacter(@Query("characterId") cardId: Int)

    @DELETE("/user/comics/delete-my-character")
    suspend fun deleteUserCharacter(@Query("characterId") cardId: Int)


}