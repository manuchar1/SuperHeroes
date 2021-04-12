package com.school_project.superHeroesApp.ui.cardDetails

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.school_project.superHeroesApp.R
import com.school_project.superHeroesApp.base.BaseFragment
import com.school_project.superHeroesApp.data.models.superhero.Content
import com.school_project.superHeroesApp.databinding.DetailedCardBinding
import com.school_project.superHeroesApp.ui.login.LoginViewModel
import com.school_project.superHeroesApp.utils.observeEvent

class CardDetailsFragment : BaseFragment() {

    private var  progr = 0


    private var binding: DetailedCardBinding? = null

    private val cardDetailArg by navArgs<CardDetailsFragmentArgs>()

    private val viewModel by viewModels<CardDetailsViewModel> {
        CardDetailsViewModel.CardDetailsViewModelFactory(cardDetailArg.data)
    }

    private val loginViewModel by activityViewModels<LoginViewModel>()

    override fun getViewModelInstance() = viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DetailedCardBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.cardModel.observe(viewLifecycleOwner) {
            showCardData(it)
        }

        binding?.backBtn?.setOnClickListener {
            findNavController().popBackStack()
        }
        binding?.addRemoveBtn?.setOnClickListener {
            viewModel.buttonClicked()
        }
        viewModel.cardSaved.observe(viewLifecycleOwner) {
            when (it) {
                CardDetailsViewModel.CardSavedState.NotSaved -> binding?.addRemoveBtn?.setText(R.string.card_details_add)
                CardDetailsViewModel.CardSavedState.Saved -> binding?.addRemoveBtn?.setText(R.string.card_details_remove)
                else -> binding?.addRemoveBtn?.setText(R.string.card_details_log_in)
            }
        }

        viewModel.loginRequired.observeEvent(viewLifecycleOwner) {
            activity?.findNavController(R.id.mainContainer)?.navigate(R.id.login)
        }

        loginViewModel.loginFinished.observeEvent(viewLifecycleOwner) {
            if (it) viewModel.determineCardSavedState()
        }
        viewModel.showConfirmDialog.observeEvent(viewLifecycleOwner) {
            AlertDialog.Builder(requireContext()).setMessage(R.string.are_you_sure_delete)
                .setPositiveButton(
                    R.string.common_yes
                ) { dialog, _ ->
                    dialog.dismiss()
                    viewModel.deleteConfirmed()
                }
                .setNeutralButton(R.string.common_no) { dialogInterface: DialogInterface, _: Int ->
                    dialogInterface.dismiss()
                }.show()
        }


    }

    @SuppressLint("SetTextI18n")
    private fun showCardData(card: Content) {
        binding?.apply {
            tvHeroName.text = card.name
            tvFullName.text = card.biography?.fullName
            tvAliases.text = card.biography?.aliases.toString()

            Glide.with(ivHeroPic).load(card.image?.url).placeholder(R.drawable.item_loader)
                .into(ivHeroPic)
            progressBar.progress = card.powerstats?.combat?.toInt()!!
            tvCompatProgress.text = card.powerstats.combat

            progressBar2.progress = card.powerstats.durability?.toInt()!!
            tvDurabilityProgress.text = card.powerstats.durability

            progressBar3.progress = card.powerstats.intelligence?.toInt()!!
            tvIntelligenceProgress.text = card.powerstats.intelligence

            progressBar4.progress = card.powerstats.power?.toInt()!!
            tvPowerProgress.text = card.powerstats.power

            progressBar5.progress = card.powerstats.strength?.toInt()!!
            tvStrengthProgress.text = card.powerstats.strength

            progressBar6.progress = card.powerstats.speed?.toInt()!!
            tvSpeedProgress.text = card.powerstats.speed


            tvEye.text = card.appearance?.eyeColor
            tvGender.text = card.appearance?.gender
            tvHairColor.text = card.appearance?.hairColor
            textView.text = card.appearance?.height.toString()
            tvRace.text = card.appearance?.race
            tvWeight.text = card.appearance?.weight.toString()
            tvBase.text = card.work?.base
            tvOccupation.text = card.work?.occupation
            tvConnection2.text = card.connections?.groupAffiliation

        }
    }

}
