package com.school_project.superHeroesApp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.school_project.superHeroesApp.R
import com.school_project.superHeroesApp.databinding.SearchScreenBinding
import com.school_project.superHeroesApp.ui.cardDetails.CardDetailsFragmentDirections
import com.school_project.superHeroesApp.ui.home.CardAdapter
import com.school_project.superHeroesApp.utils.HeroCardDecorator

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()

    private var binding: SearchScreenBinding? = null

    private val adapter = CardAdapter() {
        val action = CardDetailsFragmentDirections.actionGlobalCardDetailsFragment(it)
        activity?.findNavController(R.id.mainContainer)
            ?.navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchScreenBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = GridLayoutManager(context, 2)
        layoutManager.spanSizeLookup = CardAdapter.LoaderSpanSizeLookup(adapter)
        binding?.recycleView?.layoutManager = layoutManager
        binding?.recycleView?.adapter = adapter
        binding?.recycleView?.addItemDecoration(
            HeroCardDecorator(
                itemHorizontalInsets = resources.getDimensionPixelSize(R.dimen.card_list_hor_inst),
                itemHorizontalSpacing = resources.getDimensionPixelSize(R.dimen.card_list_hor_space),
                itemVerticalInsets = resources.getDimensionPixelSize(R.dimen.card_list_ver_inst),
                itemVerticalSpacing = resources.getDimensionPixelSize(R.dimen.card_list_vrt_space),
            )
        )
        viewModel.cards.observe(viewLifecycleOwner) {
            adapter.cardList = it
        }

        binding?.searchInput?.doOnTextChanged { text, _, _, _ ->
            viewModel.onSearchTextChange(text)
        }
        viewModel.message.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }

    }


}