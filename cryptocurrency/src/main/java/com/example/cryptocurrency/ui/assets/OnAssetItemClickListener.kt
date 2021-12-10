package com.example.cryptocurrency.ui.assets

interface OnAssetItemClickListener {

    /**
     * Callback when a 'Cryptographic Asset' from the list is clicked
     */
    fun onAssetItemClicked(id: String, rank: String)
}