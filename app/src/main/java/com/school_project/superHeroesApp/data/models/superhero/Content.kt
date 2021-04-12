package com.school_project.superHeroesApp.data.models.superhero

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
@Entity
data class Content(
    @Json(name = "id")
    @PrimaryKey
    val id: Int,
    @Json(name = "appearance")
    @Embedded
    val appearance: Appearance?,
    @Json(name = "biography")
    @Embedded
    val biography: Biography?,
    @Json(name = "connections")
    @Embedded
    val connections: Connections?,
    @Json(name = "image")
    @Embedded
    val image: Image?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "powerstats")
    @Embedded
    val powerstats: Powerstats?,
    @Json(name = "work")
    @Embedded
    val work: Work?
): Parcelable