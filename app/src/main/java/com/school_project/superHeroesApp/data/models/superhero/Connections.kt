package com.school_project.superHeroesApp.data.models.superhero


import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class Connections(
    @Json(name = "group-affiliation")
    val groupAffiliation: String,
    @Json(name = "relatives")
    val relatives: String
): Parcelable