package com.school_project.superHeroesApp.ui.navHome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.school_project.superHeroesApp.R
import com.school_project.superHeroesApp.databinding.NavBarMenuBinding

class NavHomeFragment : Fragment() {

    private var binding: NavBarMenuBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NavBarMenuBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.homeNavContainer) as NavHostFragment
        val navController = navHostFragment.navController
        binding?.homeNavTabBar?.setupWithNavController(navController)

        
    }

}