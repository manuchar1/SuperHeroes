package com.school_project.superHeroesApp.data.models.superhero

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class Data<T>(
    @Json(name = "data")
    val data: T
)