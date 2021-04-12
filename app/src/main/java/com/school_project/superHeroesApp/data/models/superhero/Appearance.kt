package com.school_project.superHeroesApp.data.models.superhero


import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class Appearance(
    @Json(name = "eye-color")
    val eyeColor: String,
    @Json(name = "gender")
    val gender: String,
    @Json(name = "hair-color")
    val hairColor: String,
    @Json(name = "height")
    val height: List<String>,
    @Json(name = "race")
    val race: String,
    @Json(name = "weight")
    val weight: List<String>
): Parcelable