package com.example.cryptocurrency.ui.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cryptocurrency.R
import com.example.cryptocurrency.Utils
import com.example.cryptocurrency.databinding.FragmentMarketBinding
import com.example.cryptocurrency.viewmodel.market.MarketViewModel
import com.example.cryptocurrency.viewmodel.market.MarketViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Fragment displaying the market details of a 'Cryptogarphic Asset'
 */

@AndroidEntryPoint
class MarketFragment : Fragment() {

    private lateinit var binding: FragmentMarketBinding

    @Inject
    lateinit var viewModelFactory: MarketViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMarketBinding.inflate(layoutInflater)

        val id = arguments?.getString("id", "abc").toString()
        val rank: String = arguments?.getString("rank", "N/A").toString()

        val viewModel: MarketViewModel = ViewModelProvider(this, viewModelFactory).get(
            MarketViewModel::class.java
        )
        viewModel.setID(id)



        viewModel.marketDetails.observe(viewLifecycleOwner, {
            binding.errorText.visibility = View.GONE
            if (it != null) {
                displayDetails(it, rank)
            } else {
                binding.group.visibility = View.GONE
                binding.errorText.visibility = View.VISIBLE
                binding.errorText.text = getString(R.string.try_again)
            }

        })

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            binding.group.visibility = View.GONE
            binding.errorText.visibility = View.VISIBLE
            binding.errorText.text = it + getString(R.string.try_again)
        })

        viewModel.loading.observe(viewLifecycleOwner, {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })

        viewModel.getMarketDetails()

        return binding.root
    }

    /**
     * method to display the details - exchange ID,
     * rank, price and date
     * Displaying only the item that had the highest
     * volume transacted in the last 24 hrs
     */
    private fun displayDetails(
        it: List<MarketUiItem>,
        rank: String
    ) {
        val listItems = it.toMutableList()
        listItems.sortByDescending { it.volumeUsd24Hr }
        binding.textViewExchangeId.text = listItems[0].exchangeId
        binding.textViewPrice.text = listItems[0].price
        binding.textViewRank.text = rank
        if (listItems[0].date.equals("null"))
            binding.textViewDate.text = Utils().getCurrentDate()
        else
            binding.textViewDate.text = listItems[0].date
    }
}