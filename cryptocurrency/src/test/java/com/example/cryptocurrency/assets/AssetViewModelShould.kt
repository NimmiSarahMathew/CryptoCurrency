package com.example.cryptocurrency.assets

import com.example.cryptocurrency.BaseUnitTest
import com.example.cryptocurrency.data.model.ResponseState
import com.example.cryptocurrency.data.model.assets.AssetData
import com.example.cryptocurrency.data.model.assets.AssetsApiData
import com.example.cryptocurrency.data.repository.AssetsRepository
import com.example.cryptocurrency.getValueForTest
import com.example.cryptocurrency.ui.assets.AssetUiItem
import com.example.cryptocurrency.viewmodel.assets.AssetsViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.junit.jupiter.api.Assertions


class AssetViewModelShould : BaseUnitTest() {

    private val repo: AssetsRepository = mock()
    private val asset: List<AssetUiItem> =
        listOf(AssetUiItem("id", "symbol", "name", "price", "rank"))
    private val exception = RuntimeException("Server is down")
    private val viewModel = AssetsViewModel(repo)


    /**
     * Test to validate whether the Viewmodel class is
     * making a call to the repository class to receive
     * data
     */
    @Test
    fun getAssetsListFromRepository() = runBlockingTest {

        viewModel.getAllAssets()
        verify(repo, times(1)).getAssets()
    }


    /**
     * Test to validate whether the view model
     * is emitting the result received from
     * the repository
     */
    @Test
    fun emitAssetsFromRepository() = runBlockingTest {
        mockUpSuccess()
        viewModel.getAllAssets()
        Assertions.assertEquals(asset, viewModel.assetsList.getValueForTest())
    }


    /**
     * Test to validate an error result
     * from the repository
     */
    @Test
    fun emitErrorWhenReceivedError() {
        mockUpError()
        viewModel.getAllAssets()
        Assert.assertEquals(
            exception.message,
            viewModel.errorMessage.getValueForTest()
        )
    }

    private fun mockUpError(): AssetsViewModel {
        runBlockingTest {

            whenever(repo.getAssets()).thenReturn(
                ResponseState.Error("Server is down")
            )
        }

        return AssetsViewModel(repo)
    }

    private fun mockUpSuccess(): AssetsViewModel {
        runBlockingTest {
            whenever(repo.getAssets()).thenReturn(
                ResponseState.Success(
                    AssetsApiData(
                        listOf(
                            AssetData(
                                "changePercent",
                                "explorer",
                                "id",
                                "marketCapUsd",
                                "marketSupply",
                                "name",
                                "price",
                                "rank",
                                "supply",
                                "symbol",
                                "volumeUsd24Hr",
                                "vwap24Hr"
                            )
                        ), 10L
                    )
                )
            )
        }
        return AssetsViewModel(repo)
    }
}