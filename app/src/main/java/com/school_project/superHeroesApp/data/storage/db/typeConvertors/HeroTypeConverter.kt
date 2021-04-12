package com.school_project.superHeroesApp.data.storage.db.typeConvertors

import androidx.room.TypeConverter
import com.school_project.superHeroesApp.data.models.superhero.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

private object Serializer {
    val instance: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val powerstatsAdapter: JsonAdapter<Powerstats> = instance.adapter<Powerstats>(
        Types.newParameterizedType(
            List::class.java,
            Powerstats::class.java
        )
    )
    val appearanceAdapter: JsonAdapter<Appearance> = instance.adapter<Appearance>(
        Types.newParameterizedType(
            List::class.java,
            Appearance::class.java
        )
    )
    val connectionsAdapter: JsonAdapter<Connections> = instance.adapter<Connections>(
        Types.newParameterizedType(
            List::class.java,
            Connections::class.java
        )
    )
    val workAdapter: JsonAdapter<Work> = instance.adapter<Work>(
        Types.newParameterizedType(
            List::class.java,
            Work::class.java
        )
    )

    val biographyAdapter: JsonAdapter<Biography> = instance.adapter<Biography>(
        Types.newParameterizedType(
            List::class.java,
            Biography::class.java
        )
    )
}

class PowerstatsTypeConverter {

    @TypeConverter
    fun toString(powerstats: Powerstats?): String? {
        return powerstats?.let { Serializer.powerstatsAdapter.toJson(it) }
    }

    @TypeConverter
    fun fromString(string: String?) = string?.let { Serializer.powerstatsAdapter.fromJson(it) }

}

class AppearanceTypeConverter {

    @TypeConverter
    fun toString(appearance: Appearance?): String? {
        return appearance?.let { Serializer.appearanceAdapter.toJson(it) }
    }

    @TypeConverter
    fun fromString(string: String?) = string?.let { Serializer.appearanceAdapter.fromJson(it) }

}

class ConnectionTypeConverter {

    @TypeConverter
    fun toString(connection: Connections?): String? {
        return connection?.let { Serializer.connectionsAdapter.toJson(it) }
    }

    @TypeConverter
    fun fromString(string: String?) = string?.let { Serializer.connectionsAdapter.fromJson(it) }

}

class WorkTypeConverter {

    @TypeConverter
    fun toString(work: Work?): String? {
        return work?.let { Serializer.workAdapter.toJson(it) }
    }

    @TypeConverter
    fun fromString(string: String?) = string?.let { Serializer.workAdapter.fromJson(it) }

}

class BiographyTypeConverter {

    @TypeConverter
    fun toString(biography: Biography?): String? {
        return biography?.let { Serializer.biographyAdapter.toJson(it) }
    }

    @TypeConverter
    fun fromString(string: String?) = string?.let { Serializer.biographyAdapter.fromJson(it) }

}