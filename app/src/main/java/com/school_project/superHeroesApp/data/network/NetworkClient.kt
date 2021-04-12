package com.school_project.superHeroesApp.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkClient {

    val userService by lazy { createUserService() }

    val heroService by lazy { createHeroService() }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private fun createUserService(): UserService {
        val retrofitBuilder = Retrofit.Builder()
        retrofitBuilder.baseUrl("https://commschool-android-api.herokuapp.com")
        retrofitBuilder.client(
            OkHttpClient().newBuilder()
                .addInterceptor(AuthInterceptor())
                .addInterceptor(loggingInterceptor)
                .build()
        )
        retrofitBuilder.addConverterFactory(mochiConvertor())
        return retrofitBuilder.build().create(UserService::class.java)
    }

    private fun createHeroService(): HeroService {
        val retrofitBuilder = Retrofit.Builder()
        retrofitBuilder.baseUrl("https://commschool-android-api.herokuapp.com")
        retrofitBuilder.client(
            OkHttpClient().newBuilder()
                .addInterceptor(loggingInterceptor).build()
        )
        retrofitBuilder.addConverterFactory(mochiConvertor())
        return retrofitBuilder.build().create(HeroService::class.java)
    }


    private fun mochiConvertor() =
        MoshiConverterFactory.create(
            Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
        )

}