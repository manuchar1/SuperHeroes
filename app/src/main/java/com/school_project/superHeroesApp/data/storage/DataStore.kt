package com.school_project.superHeroesApp.data.storage

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.impl.WorkDatabaseMigrations.MIGRATION_1_2
import com.school_project.superHeroesApp.data.storage.db.HeroDB
import java.util.*


object DataStore {

    private const val KEY_LANGUAGE = "key_language"
    private const val KEY_TOKEN = "key_token"
    private const val KEY_LAST_TIME_SAVED_CARDS_FETCHED = "key_last_time_saved_cards_fetched"
    private var sharedPreferences: SharedPreferences? = null

    private var dataBase: HeroDB? = null

    val db get() = dataBase ?: throw RuntimeException("not initialized!!")

    fun initialize(context: Context, sharedPreferences: SharedPreferences) {
        DataStore.sharedPreferences = sharedPreferences
        dataBase = Room.databaseBuilder(
            context.applicationContext,
            HeroDB::class.java, "Sample.db"
        )
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .build()
    }

    var language: String
        @SuppressLint("ApplySharedPref")
        set(value) {
            sharedPreferences?.edit()?.putString(KEY_LANGUAGE, value)?.commit()
        }
        get() {
            return sharedPreferences?.getString(KEY_LANGUAGE, Locale.getDefault().language)
                ?: throw RuntimeException("not initialized!!")
        }


    var authToken: String?
        @SuppressLint("ApplySharedPref")
        set(value) {
            sharedPreferences?.edit()?.putString(KEY_TOKEN, value)?.commit()
        }
        get() {
            return (sharedPreferences ?: throw RuntimeException("not initialized!!"))
                .getString(KEY_TOKEN, null)
        }

    var lastTimeSavedCardsFetched: Long
        @SuppressLint("ApplySharedPref")
        set(value) {
            sharedPreferences?.edit()?.putLong(KEY_LAST_TIME_SAVED_CARDS_FETCHED, value)?.commit()
        }
        get() {
            return (sharedPreferences ?: throw RuntimeException("not initialized!!"))
                .getLong(KEY_LAST_TIME_SAVED_CARDS_FETCHED, 0)
        }


    val MIGRATION_2_3: Migration = object:Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(("ALTER TABLE users " + " ADD COLUMN last_update INTEGER"))
        }
    }
}