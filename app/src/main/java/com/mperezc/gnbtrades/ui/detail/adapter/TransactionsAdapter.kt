package com.mperezc.gnbtrades.ui.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mperezc.domain.model.TransactionModel
import com.mperezc.domain.usecases.ProductUseCase
import com.mperezc.domain.usecases.impl.ProductUseCaseImpl
import com.mperezc.gnbtrades.R
import com.mperezc.gnbtrades.common.MyUtils
import com.mperezc.gnbtrades.common.MyUtils.roundDoubleAmount
import com.mperezc.gnbtrades.databinding.ItemTransactionBinding

class TransactionsAdapter(
    private val context: Context,
    private val _transactions: List<TransactionModel>
): RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTransactionBinding.inflate(inflater, parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val movement = _transactions[position]
        holder.binding.tvAmount.text = context.getString(
            R.string.detail_amount, 
            roundDoubleAmount(movement.amountEur),
            ProductUseCase.Companion.DEFAULT_CURRENCY
        )
        holder.binding.tvOriginalAmount.text = context.getString(
            R.string.detail_original_currency,
            roundDoubleAmount(movement.amount),
            movement.currency
        )
    }

    override fun getItemCount(): Int {
        return _transactions.size
    }

    class TransactionViewHolder(
        val binding: ItemTransactionBinding
    ): RecyclerView.ViewHolder(binding.root) {}
}