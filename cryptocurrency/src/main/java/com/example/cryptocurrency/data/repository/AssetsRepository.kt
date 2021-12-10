package com.example.cryptocurrency.data.repository

import android.util.Log
import com.example.cryptocurrency.CoroutinesQualifiers
import com.example.cryptocurrency.Utils
import com.example.cryptocurrency.data.model.CoincapService
import com.example.cryptocurrency.data.model.ResponseState
import com.example.cryptocurrency.data.model.assets.AssetsApiData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class AssetsRepository @Inject constructor(
    private val service: CoincapService,
    @CoroutinesQualifiers.IoDispatcher private val dispatcher: CoroutineDispatcher
) {


    /**
     * Method to receive response(List of Assets)
     * from CoinCapService
     */
    suspend fun getAssets(): ResponseState<Any?> {
        // Moving the execution of the coroutine to the I/O dispatcher
        return withContext(dispatcher) {
            try {
                val response = service.getAssets()
                ResponseState.Success(response)
            } catch (e: Exception) {
                ResponseState.Error(e.message.toString())
            }
        }

    }


}