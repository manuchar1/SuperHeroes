package com.school_project.superHeroesApp.ui.main

import android.os.Bundle
import com.school_project.superHeroesApp.base.LanguageAwareActivity
import com.school_project.superHeroesApp.databinding.ActivityMainBinding

class MainActivity : LanguageAwareActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}

