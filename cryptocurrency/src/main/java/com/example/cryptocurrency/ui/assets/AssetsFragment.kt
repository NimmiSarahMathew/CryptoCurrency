package com.example.cryptocurrency.ui.assets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.FragmentAssetsBinding
import com.example.cryptocurrency.viewmodel.assets.AssetsViewModel
import com.example.cryptocurrency.viewmodel.assets.AssetsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Fragment to display the list of cryptographic assets
 */
@AndroidEntryPoint
class AssetsFragment : Fragment(), OnAssetItemClickListener {

    private lateinit var binding: FragmentAssetsBinding

    @Inject
    lateinit var viewModelFactory: AssetsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAssetsBinding.inflate(layoutInflater)


        val viewmodel: AssetsViewModel =
            ViewModelProvider(this, viewModelFactory).get(AssetsViewModel::class.java)

        /**
         * code to populate the list on
         * receiving the result from Repository/Service
         */
        viewmodel.assetsList.observe(viewLifecycleOwner, {

            if (it != null) {
                binding.recyclerViewAssets.adapter = AssetsAdapter(it, this)
                binding.errorText.visibility = View.GONE
            } else
                binding.errorText.visibility = View.VISIBLE
            binding.errorText.text = getString(R.string.try_again)

        })

        viewmodel.errorMessage.observe(viewLifecycleOwner, {
            binding.recyclerViewAssets.visibility = View.GONE
            binding.errorText.visibility = View.VISIBLE
            binding.errorText.text = it + getString(R.string.try_again)
        })

        viewmodel.loading.observe(viewLifecycleOwner, {
            if (it) {
                binding.progressBarAssets.visibility = View.VISIBLE
            } else {
                binding.progressBarAssets.visibility = View.GONE
            }
        })

        viewmodel.getAllAssets()
        return binding.root
    }

    override fun onAssetItemClicked(id: String, rank: String) {
        val extras = Bundle()
        extras.putString("id", id)
        extras.putString("rank", rank)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, extras)
    }
}