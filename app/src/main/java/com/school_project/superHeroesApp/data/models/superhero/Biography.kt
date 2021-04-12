package com.school_project.superHeroesApp.data.models.superhero


import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class Biography(
    @Json(name = "aliases")
    val aliases: List<String>,
    @Json(name = "alignment")
    val alignment: String,
    @Json(name = "alter-egos")
    val alterEgos: String,
    @Json(name = "first-appearance")
    val firstAppearance: String,
    @Json(name = "full-name")
    val fullName: String,
    @Json(name = "place-of-birth")
    val placeOfBirth: String,
    @Json(name = "publisher")
    val publisher: String
): Parcelable