package com.school_project.superHeroesApp.data.models.superhero


import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class Image(
    @Json(name = "url")
    val url: String
): Parcelable