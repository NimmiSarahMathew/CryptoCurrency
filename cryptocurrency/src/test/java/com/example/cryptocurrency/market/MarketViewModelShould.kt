package com.example.cryptocurrency.market

import com.example.cryptocurrency.BaseUnitTest
import com.example.cryptocurrency.data.model.ResponseState
import com.example.cryptocurrency.data.model.market.MarketData
import com.example.cryptocurrency.data.model.market.MarketsApiData
import com.example.cryptocurrency.data.repository.MarketRepository
import com.example.cryptocurrency.getValueForTest
import com.example.cryptocurrency.ui.market.MarketUiItem
import com.example.cryptocurrency.viewmodel.market.MarketViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MarketViewModelShould : BaseUnitTest() {

    private val repo: MarketRepository = mock()
    private val market: List<MarketUiItem> =
        listOf(MarketUiItem("exchangeId", "rank", "price", "10122021", "volumeUsd24Hr"))
    private val exception = RuntimeException("Something went wrong")
    private val viewModel = MarketViewModel(repo)

    @Before
    fun setUp(){
        viewModel.setID("bitcoin")
    }

    /**
     * Test to validate whether the viewmodel class
     * is making a call to the repository class
     */
    @Test
    fun getMarketDetailsFromRepository() = runBlockingTest {

        viewModel.getMarketDetails()
        verify(repo, times(1)).getMarkets("bitcoin")
    }


    /**
     * Test to validate whether the view model
     * is emitting the result received from
     * the repository
     */
    @Test
    fun emitAssetsFromRepository() = runBlockingTest {
        mockUpSuccess()
        viewModel.getMarketDetails()
        Assert.assertEquals(market, viewModel.marketDetails.getValueForTest())
    }


    /**
     * Test to validate an error result
     * from the repository
     */
    @Test
    fun emitErrorWhenReceivedError() {
        mockUpError()
        viewModel.getMarketDetails()
        Assert.assertEquals(
            exception.message,
            viewModel.errorMessage.getValueForTest()
        )
    }

    private fun mockUpError(){
        runBlocking {
            whenever(repo.getMarkets("bitcoin")).thenReturn(
                ResponseState.Error("Something went wrong")
            )

        }

    }

    private fun mockUpSuccess(){
        runBlocking {
            whenever(repo.getMarkets("bitcoin")).thenReturn(
                ResponseState.Success(
                    MarketsApiData(
                        listOf(
                            MarketData(
                                "baseId",
                                "baseSymbol",
                                "exchangeId",
                                "percentExchangeVolume",
                                "priceQuote",
                                "price",
                                "quoteId",
                                "quoteSymbol",
                                "rank",
                                "tradesCount24Hr",
                                10122021L,
                                "volumeUsd24Hr"
                            )
                        ), 10L
                    )
                )
            )
        }
    }

}