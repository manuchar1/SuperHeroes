package com.school_project.superHeroesApp.ui.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels

import androidx.navigation.fragment.findNavController

import com.school_project.superHeroesApp.R
import com.school_project.superHeroesApp.base.BaseFragment
import com.school_project.superHeroesApp.databinding.RegisterFragmentBinding

class RegistrationFragment : BaseFragment() {

    private var binding: RegisterFragmentBinding? = null

    private val viewModel by viewModels<RegistrationViewModel>()

    override fun getViewModelInstance() = viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegisterFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.BackIcon?.setOnClickListener { activity?.onBackPressed() }
        binding?.singUp?.setOnClickListener {
            viewModel.onRegister(

                username = binding?.regUserNameInput?.text,
                name = binding?.regNameInput?.text,
                password = binding?.regPassword?.text,
                repeatPassword = binding?.rpPassword?.text
            )
        }
        viewModel.validationError.observe(viewLifecycleOwner, this::showValidationError)
        viewModel.registrationComplete.observe(viewLifecycleOwner) {
            findNavController().popBackStack(R.id.loginFragment, true)
        }
    }

    private fun showValidationError(error: RegistrationViewModel.ValidationError) {
        binding?.apply {
            when (error) {
                RegistrationViewModel.ValidationError.EmptyName -> {
                    regNameInput.error = getString(R.string.registration_error_empty_name)
                }

                RegistrationViewModel.ValidationError.EmptyUsername -> {
                    regUserNameInput.error = getString(R.string.registration_error_empty_username)
                }

                RegistrationViewModel.ValidationError.EmptyPassword -> {
                    regPassword.error = getString(R.string.registration_error_empty_password)
                }
                RegistrationViewModel.ValidationError.PasswordsNotMatching -> {
                    rpPassword.error =
                        getString(R.string.registration_error_passwords_not_matching)
                }
                RegistrationViewModel.ValidationError.None -> {

                    regNameInput.error = null
                    regUserNameInput.error = null
                    regPassword.error = null
                    rpPassword.error = null
                }
            }
        }

    }


    companion object {
        const val KEY_USERNAME = "key_username"
        const val KEY_DATA = "key_data"
    }

}