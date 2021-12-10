package com.example.cryptocurrency.market

import com.example.cryptocurrency.data.model.CoincapService
import com.example.cryptocurrency.data.model.ResponseState
import com.example.cryptocurrency.data.model.market.MarketsApiData
import com.example.cryptocurrency.data.repository.MarketRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.jupiter.api.Assertions

class MarketRepositoryShould{

    private val service: CoincapService = mock()
    private val marketDetails: MarketsApiData = mock()
    private val testDispatcher = TestCoroutineDispatcher()

    /**
     * Test to validate whether Repository class is
     *  making a call to CoinCapService
     */
    @Test
    fun getMarketDataFromService(){
        runBlockingTest {
            val repository = MarketRepository(service, testDispatcher)
            repository.getMarkets("bitcoin")

            verify(service, times(1)).getMarkets("bitcoin")
        }

    }

    /**
     * Test to validate whether the repository is emitting the
     * result received from the CoinCapService
     */
    @Test
    fun emitMarketDetailsOnReceivingFromService() = runBlockingTest{

        whenever(service.getMarkets("bitcoin"))
                .thenReturn(marketDetails)

        val repository = MarketRepository(service, testDispatcher)

        Assertions.assertEquals(ResponseState.Success(marketDetails), repository.getMarkets("bitcoin"))
    }


    /**
     * Test to validate an error result
     * from the CoinCapService
     */
    @Test
    fun emitErrorOnReceivingErrorFromService()= runBlockingTest{

        whenever(service.getMarkets("bitcoin"))
                .thenThrow(RuntimeException("Server is down"))

        val repository = MarketRepository(service, testDispatcher)

        Assertions.assertEquals(ResponseState.Error("Server is down"), repository.getMarkets("bitcoin"))

    }


}