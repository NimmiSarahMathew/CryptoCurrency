package com.example.cryptocurrency.data.model

import com.example.cryptocurrency.data.model.assets.AssetsApiData
import com.example.cryptocurrency.data.model.market.MarketsApiData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

const val CAPCOIN_ENDPOINT_HOST = "https://api.coincap.io/"

interface CoincapService {

    @GET("/v2/assets")
    suspend fun getAssets(): AssetsApiData

    @GET("/v2/assets/{id}/markets")
    suspend fun getMarkets(@Path("id") id: String): MarketsApiData
}