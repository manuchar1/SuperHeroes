package com.school_project.superHeroesApp.data.models.superhero


import androidx.annotation.Keep
import com.squareup.moshi.Json


@Keep
data class PaginatedData<T>(
    @Json(name = "content")
    val content: T,
    @Json(name = "isLast")
    val isLast: Boolean,
    @Json(name = "offset")
    val offset: Int,
    @Json(name = "totalCount")
    val totalCount: Int
)