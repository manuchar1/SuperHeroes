package com.school_project.superHeroesApp.ui.savedCards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.school_project.superHeroesApp.R
import com.school_project.superHeroesApp.base.BaseFragment
import com.school_project.superHeroesApp.databinding.FavoritesBinding
import com.school_project.superHeroesApp.ui.cardDetails.CardDetailsFragmentDirections
import com.school_project.superHeroesApp.ui.home.CardAdapter
import com.school_project.superHeroesApp.ui.login.LoginViewModel
import com.school_project.superHeroesApp.utils.HeroCardDecorator
import com.school_project.superHeroesApp.utils.observeEvent

class FavoritesFragment : BaseFragment() {

    private val viewModel by viewModels<FavoritesViewModel>()

    private val loginViewModel by activityViewModels<LoginViewModel>()

    override fun getViewModelInstance() = viewModel

    private var binding: FavoritesBinding? = null

    private var adapter = CardAdapter {
        val action = CardDetailsFragmentDirections.actionGlobalCardDetailsFragment(it)
        activity?.findNavController(R.id.mainContainer)?.navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FavoritesBinding.inflate(inflater, container, false)
        return binding?.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = GridLayoutManager(context, 2)
        layoutManager.spanSizeLookup = CardAdapter.LoaderSpanSizeLookup(adapter)
        binding?.apply {
            recycleView.layoutManager = layoutManager
            recycleView.adapter = adapter
            recycleView.addItemDecoration(
                HeroCardDecorator(
                    itemHorizontalInsets = resources.getDimensionPixelSize(R.dimen.card_list_hor_inst),
                    itemHorizontalSpacing = resources.getDimensionPixelSize(R.dimen.card_list_hor_space),
                    itemVerticalInsets = resources.getDimensionPixelSize(R.dimen.card_list_ver_inst),
                    itemVerticalSpacing = resources.getDimensionPixelSize(R.dimen.card_list_vrt_space),
                )
            )



            swipeToRefresh.setOnRefreshListener {
                viewModel.refresh()
                swipeToRefresh.isRefreshing = false
            }
        }
        viewModel.requestLogin.observeEvent(viewLifecycleOwner) {
            login()
        }
        viewModel.userCards.observe(viewLifecycleOwner) {
            adapter.cardList = it
        }
        loginViewModel.loginFinished.observeEvent(viewLifecycleOwner) { loginSuccess ->
            if (loginSuccess)
                viewModel.getSavedCards()
            else
                findNavController().navigate(R.id.show_home)
        }

        //val quantity = binding?.recycleView?.adapter?.itemCount


    }


    private fun login() {
        activity?.findNavController(R.id.mainContainer)?.navigate(R.id.login)
    }





}