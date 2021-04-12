package com.school_project.superHeroesApp.app

import android.app.Application
import android.content.Context
import com.school_project.superHeroesApp.data.storage.DataStore

class SuperHeroApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DataStore.initialize(this, getSharedPreferences("_sh_", Context.MODE_PRIVATE))
    }


}