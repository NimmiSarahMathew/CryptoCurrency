package com.example.cryptocurrency.viewmodel.assets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrency.data.model.ResponseState
import com.example.cryptocurrency.data.model.assets.AssetsApiData
import com.example.cryptocurrency.data.repository.AssetsRepository
import com.example.cryptocurrency.ui.assets.AssetUiItem
import kotlinx.coroutines.launch

class AssetsViewModel(val repo: AssetsRepository) : ViewModel() {


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


    private val _assetsList = MutableLiveData<List<AssetUiItem>?>()
    val assetsList: LiveData<List<AssetUiItem>?>
        get() {
            return _assetsList
        }


    /**
     * method to get the list of assets
     * from repository
     */
    fun getAllAssets() {
        viewModelScope.launch {

            val result = repo.getAssets()

            _loading.value = false

            when (result) {
                is ResponseState.Success -> displayAssetsList(result)
                is ResponseState.Error -> displayError(result)
            }
        }

    }


    private fun displayAssetsList(result: ResponseState.Success<Any?>) {
        _assetsList.value = (result.data as AssetsApiData).assetData?.map { assetData ->
            AssetUiItem(
                id = assetData.id.toString(),
                symbol = assetData.symbol.toString(),
                name = assetData.name.toString(),
                price = assetData.priceUsd.toString(),
                rank = assetData.rank.toString()
            )
        }
    }

    private fun displayError(result: ResponseState.Error) {
        _errorMessage.value =
            "${result.exception}"
    }


}