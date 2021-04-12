package com.school_project.superHeroesApp.data.models.superhero


import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class Powerstats(
    @Json(name = "combat")
    val combat: String?,
    @Json(name = "durability")
    val durability: String?,
    @Json(name = "intelligence")
    val intelligence: String?,
    @Json(name = "power")
    val power: String?,
    @Json(name = "speed")
    val speed: String?,
    @Json(name = "strength")
    val strength: String?
): Parcelable