package com.school_project.superHeroesApp.data.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.school_project.superHeroesApp.data.models.superhero.Content
import com.school_project.superHeroesApp.data.models.user.UserProfile
import com.school_project.superHeroesApp.data.storage.db.entities.SavedCardIdEntity
import com.school_project.superHeroesApp.data.storage.db.typeConvertors.*

@Database( entities = [Content::class, SavedCardIdEntity::class, UserProfile::class],
    version = 3)
@TypeConverters(
    StringListTypeConverter::class,
    WorkTypeConverter::class,
    BiographyTypeConverter::class,
    ConnectionTypeConverter::class,
    AppearanceTypeConverter::class,
    PowerstatsTypeConverter::class
)
abstract class HeroDB : RoomDatabase() {
    abstract fun getCardDAO(): CardDao
    abstract fun getSavedCardsDao(): SavedCardIdDao
    abstract fun getUserProfileDAO(): UserProfileDao
}


