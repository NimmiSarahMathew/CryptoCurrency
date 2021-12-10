package com.example.cryptocurrency.ui.market

data class MarketUiItem(
    val exchangeId: String,
    val rank: String = "N/A",
    val price: String,
    val date: String,
    val volumeUsd24Hr: String
)
