package com.mperezc.gnbtrades.ui.transaction.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mperezc.domain.model.ProductModel
import com.mperezc.gnbtrades.databinding.ItemProductBinding

class ProductsAdapter(
    private val onItemClicked: ((ProductModel) -> Unit)?
): RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    private val _products: MutableList<ProductModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = _products[position]
        holder.binding.txProduct.text = product.sku
        holder.binding.root.setOnClickListener {
            onItemClicked?.invoke(product)
        }
    }

    override fun getItemCount(): Int {
        return _products.size
    }

    fun refresh(products: List<ProductModel>) {
        _products.clear()
        _products.addAll(products)
        notifyDataSetChanged()
    }

    class ProductViewHolder(
        val binding: ItemProductBinding
    ): RecyclerView.ViewHolder(binding.root) {}
}