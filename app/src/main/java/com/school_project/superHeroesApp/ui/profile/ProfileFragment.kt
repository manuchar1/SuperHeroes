package com.school_project.superHeroesApp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.school_project.superHeroesApp.R
import com.school_project.superHeroesApp.base.BaseFragment
import com.school_project.superHeroesApp.data.models.user.UserProfile
import com.school_project.superHeroesApp.data.storage.DataStore
import com.school_project.superHeroesApp.databinding.FragmentUserProfileBinding
import com.school_project.superHeroesApp.ui.login.LoginViewModel
import com.school_project.superHeroesApp.utils.observeEvent
import kotlinx.coroutines.InternalCoroutinesApi

class ProfileFragment : BaseFragment() {

    private var binding: FragmentUserProfileBinding? = null

    private val viewModel by viewModels<ProfileViewModel>()

    private val loginViewModel by activityViewModels<LoginViewModel>()

    override fun getViewModelInstance() = viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.imageBtnEn?.setOnClickListener {
            DataStore.language = "en"
            activity?.recreate()
        }
        binding?.imageBtnGe?.setOnClickListener {
            DataStore.language = "ka"
            activity?.recreate()
        }
        binding?.logout?.setOnClickListener {
            loginViewModel.logOut() //todo
            findNavController().navigate(R.id.show_home)
        }

        viewModel.userProfile.observe(viewLifecycleOwner, this::showUserData)
        viewModel.loginRequired.observeEvent(viewLifecycleOwner) {
            loginViewModel.logOut() //todo
            activity?.findNavController(R.id.mainContainer)?.navigate(R.id.login)
        }
        loginViewModel.loginFinished.observeEvent(viewLifecycleOwner) { loginSuccess ->
            if (loginSuccess)
                viewModel.getProfile()
            else
                findNavController().navigate(R.id.show_home)
        }
    }



    @InternalCoroutinesApi
    private fun showUserData(userProfile: UserProfile) {
        binding?.tvUserName?.text = userProfile.userName
        binding?.tvName?.text = userProfile.name
        Glide.with(this@ProfileFragment)
            .load(userProfile.imageUrl)
            .centerCrop()
            .into(binding?.imProfilePic!!)

    }

}