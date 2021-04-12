package com.school_project.superHeroesApp.data.storage.db.typeConvertors

import androidx.room.TypeConverter

class StringListTypeConverter {
    @TypeConverter
    fun toStringList(string: String?): List<String>? {
        return string?.split("|")?.toList()
    }

    @TypeConverter
    fun listToString(string: List<String>?): String? {
        return string?.joinToString("|")
    }
}