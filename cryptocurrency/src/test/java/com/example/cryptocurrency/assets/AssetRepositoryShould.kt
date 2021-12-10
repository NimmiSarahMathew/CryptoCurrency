package com.example.cryptocurrency.assets

import com.example.cryptocurrency.data.model.CoincapService
import com.example.cryptocurrency.data.model.ResponseState
import com.example.cryptocurrency.data.model.assets.AssetsApiData
import com.example.cryptocurrency.data.repository.AssetsRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.jupiter.api.Assertions


class AssetRepositoryShould {

    private val service: CoincapService = mock()
    private val assets: AssetsApiData = mock()
    private val testDispatcher = TestCoroutineDispatcher()


    /**
     * Test to validate whether Repository class is
     *  making a call to CoinCapService
     */
    @Test
    fun getAssetsListFromService()= runBlockingTest {
            val repository = AssetsRepository(service, testDispatcher)
            repository.getAssets()

            verify(service, times(1)).getAssets()
        }




    /**
     * Test to validate whether the repository is emitting the
     * result received from the CoinCapService
     */
    @Test
    fun emitAssetsOnReceivingFromService() = runBlockingTest{

        whenever(service.getAssets())
                .thenReturn(assets)

        val repository = AssetsRepository(service, testDispatcher)

        Assertions.assertEquals(ResponseState.Success(assets), repository.getAssets())
    }


    /**
     * Test to validate an error result
     * from the CoinCapService
     */
    @Test
    fun emitErrorOnReceivingErrorFromService()= runBlockingTest{

        whenever(service.getAssets())
                .thenThrow(RuntimeException("Server is down"))

        val repository = AssetsRepository(service, testDispatcher)

        Assertions.assertEquals(ResponseState.Error("Server is down"), repository.getAssets())

    }



}