package com.example.cryptocurrency.ui.assets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrency.databinding.AssetItemBinding

/**
 * Adapter to populate the list of 'Cryptographic Assets'
 * Displaying the name, symbol and price of each asset
 */
class AssetsAdapter(private val assetItems: List<AssetUiItem>,
                    private val clickListener: OnAssetItemClickListener
) :
        RecyclerView.Adapter<AssetItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AssetItemViewHolder(
                AssetItemBinding.inflate(
                        inflater,
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: AssetItemViewHolder, position: Int) {
        holder.bind(assetItems[position])
        holder.itemView.setOnClickListener { clickListener.onAssetItemClicked(assetItems[position].id, assetItems[position].rank ) }
    }

    override fun getItemCount() = assetItems.size
}

class AssetItemViewHolder(private val binding: AssetItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    fun bind(asset: AssetUiItem) {
        binding.textViewAssetCode.text = asset.symbol
        binding.textViewAssetName.text = asset.name
        binding.textViewAssetPrice.text = asset.price
    }
}
