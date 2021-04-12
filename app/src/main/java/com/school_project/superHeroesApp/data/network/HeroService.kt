package com.school_project.superHeroesApp.data.network

import com.school_project.superHeroesApp.data.models.superhero.Content
import com.school_project.superHeroesApp.data.models.superhero.Data
import com.school_project.superHeroesApp.data.models.superhero.PaginatedData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface HeroService {
    @GET("/comics/list-characters")
    suspend fun getCards(
        @Query("query",  encoded = false) query: String? = null,
        @Query("offset") offset: Int? = null,
        @Query("pageSize") pageSize: Int? = null
    ): PaginatedData<List<Content>>

    @GET("/comics/search")
    suspend fun searchCards(
        @Query("query") query: String? = null,
        @Query("offset") offset: Int? = 1,
        @Query("pageSize") pageSize: Int? = 20
    ): PaginatedData<List<Content>>


    @GET("/comics/get{id}")
    suspend fun getCardBydId(
        @Path("id") id: Int,
    ): Data<Content>
}

