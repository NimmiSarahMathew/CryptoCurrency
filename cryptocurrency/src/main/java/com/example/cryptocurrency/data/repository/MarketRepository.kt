package com.example.cryptocurrency.data.repository

import com.example.cryptocurrency.CoroutinesQualifiers
import com.example.cryptocurrency.data.model.CoincapService
import com.example.cryptocurrency.data.model.ResponseState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MarketRepository @Inject constructor(
    private val service: CoincapService,
    @CoroutinesQualifiers.IoDispatcher private val dispatcher: CoroutineDispatcher
) {


    /**
     * Method to receive response(List of Markets)
     * from CoinCapService
     */
    suspend fun getMarkets(id: String): ResponseState<Any?> {
        // Move the execution of the coroutine to the I/O dispatcher
        return withContext(dispatcher) {
            try {
                val response = service.getMarkets(id)
                ResponseState.Success(response)
            } catch (e: Exception) {
                ResponseState.Error(e.message.toString())
            }
        }

    }
}