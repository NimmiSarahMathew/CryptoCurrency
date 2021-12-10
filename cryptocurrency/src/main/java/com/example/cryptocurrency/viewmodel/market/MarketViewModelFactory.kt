package com.example.cryptocurrency.viewmodel.market

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cryptocurrency.data.repository.MarketRepository
import javax.inject.Inject


class MarketViewModelFactory @Inject constructor(val repo: MarketRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MarketViewModel(repo) as T
    }
}