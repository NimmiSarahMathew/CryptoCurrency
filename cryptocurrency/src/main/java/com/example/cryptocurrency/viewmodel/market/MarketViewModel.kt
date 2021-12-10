package com.example.cryptocurrency.viewmodel.market

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrency.data.model.ResponseState
import com.example.cryptocurrency.data.model.market.MarketsApiData
import com.example.cryptocurrency.data.repository.MarketRepository
import com.example.cryptocurrency.ui.market.MarketUiItem
import kotlinx.coroutines.*
import javax.inject.Inject

class MarketViewModel @Inject constructor(val repo: MarketRepository) : ViewModel() {

    private lateinit var _id: String

    fun setID(id: String) {
        _id = id
    }


    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() {
            return _errorMessage
        }


    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() {
            return _loading
        }


    private val _marketDetails = MutableLiveData<List<MarketUiItem>?>()
    val marketDetails: LiveData<List<MarketUiItem>?>
        get() {
            return _marketDetails
        }


    /**
     * To receive the data as Livedata
     * from Repository
     */
    fun getMarketDetails() {

        viewModelScope.launch {
            val result = repo.getMarkets(_id)

            _loading.value = false

            when (result) {
                is ResponseState.Success -> displayMarketDetails(result)
                is ResponseState.Error -> displayError(result)
            }
        }

    }


    private fun displayMarketDetails(result: ResponseState.Success<Any?>) {
        _marketDetails.value = (result.data as MarketsApiData).marketData?.map { marketData ->
            MarketUiItem(
                exchangeId = marketData.exchangeId.toString(),
                rank = marketData.rank.toString(),
                price = marketData.priceUsd.toString(),
                date = marketData.updated.toString(),
                volumeUsd24Hr = marketData.volumeUsd24Hr.toString(),
            )
        }
    }

    private fun displayError(result: ResponseState.Error) {
        _errorMessage.value =
            "${result.exception}"
    }

}