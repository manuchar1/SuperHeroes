package com.school_project.superHeroesApp.ui.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.*
import androidx.navigation.fragment.findNavController

import com.school_project.superHeroesApp.R
import com.school_project.superHeroesApp.base.BaseFragment

import com.school_project.superHeroesApp.databinding.LoginFragmentBinding
import com.school_project.superHeroesApp.ui.registration.RegistrationFragment


class LoginFragment : BaseFragment(), View.OnClickListener {

    private var binding: LoginFragmentBinding? = null

    private val viewModel: LoginViewModel by activityViewModels()

    override fun getViewModelInstance() = viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setFragmentResult(KEY_LOGIN_RESULT, bundleOf(KEY_LOGIN_RESULT_SUCCESS to false))
        setFragmentResultListener(RegistrationFragment.KEY_DATA) { _, bundle ->
            binding?.userNameInput?.setText(bundle.getString(RegistrationFragment.KEY_USERNAME))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.loginSingUp?.setOnClickListener(this)
        binding?.btLogin?.setOnClickListener(this)
        binding?.loginBackBtn?.setOnClickListener(this)
        viewModel.inputError.observe(viewLifecycleOwner){
            binding?.passwordInput?.error=getString(it)
        }

        viewModel.loginSuccess.observe(viewLifecycleOwner) {
           findNavController().popBackStack()
        }

    }

    override fun onClick(v: View?) {
        when (v) {
            binding?.loginSingUp -> {
                startRegistration()
            }
            binding?.btLogin -> {
                viewModel.login(
                    username = binding?.userNameInput?.text,
                    password = binding?.passwordInput?.text

                )
            }
            binding?.loginBackBtn -> {
                findNavController().popBackStack()
            }


        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.loginFragmentDestroyed()
    }

    private fun startRegistration() {
        findNavController().navigate(R.id.form_login_to_registration)
    }


    companion object {
        const val KEY_LOGIN_RESULT = "key_login_result"
        const val KEY_LOGIN_RESULT_SUCCESS = "key_login_result_success"
    }
}