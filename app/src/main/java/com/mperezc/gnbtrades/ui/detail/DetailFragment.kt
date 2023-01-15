package com.mperezc.gnbtrades.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mperezc.domain.model.ProductModel
import com.mperezc.domain.usecases.CodeErrors
import com.mperezc.domain.usecases.ProductUseCase.Companion.DEFAULT_CURRENCY
import com.mperezc.gnbtrades.R
import com.mperezc.gnbtrades.common.MyUtils.roundDoubleAmount
import com.mperezc.gnbtrades.databinding.FragmentDetailBinding
import com.mperezc.gnbtrades.ui.Error
import com.mperezc.gnbtrades.ui.Loading
import com.mperezc.gnbtrades.ui.Success
import com.mperezc.gnbtrades.ui.UiConstants
import com.mperezc.gnbtrades.ui.detail.adapter.TransactionsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {

    private var binding: FragmentDetailBinding? = null
    private val viewModel: DetailViewModel by viewModel()

    private var product: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        product = arguments?.getString(UiConstants.NAVIGATION_PRODUCT_KEY)

        binding = FragmentDetailBinding.inflate(inflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProduct(product)
        setupObservers()
        setUpView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //Prevent memory leak
        binding = null
    }

    private fun setUpView() {
        binding?.let {
            with(it.rvTransactions) {
                val llm = LinearLayoutManager(requireContext())
                llm.orientation = LinearLayoutManager.VERTICAL
                layoutManager = llm

                // Set divider decoration
                val dividerItemDecoration = DividerItemDecoration(
                    requireContext(),
                    llm.orientation
                )
                addItemDecoration(dividerItemDecoration)
            }
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                when (it) {
                    is Loading -> onLoadingData()
                    is Success -> onGetProducts(it.products)
                    is Error -> onRequestFail(it.code)
                }
            }
        }
    }

    private fun onLoadingData() {
        binding?.let {
            it.llProductHeader.visibility = View.GONE
            it.rvTransactions.visibility = View.GONE
            it.tvError.visibility = View.GONE
        }

    }

    private fun onGetProducts(list: List<ProductModel>) {
        list.firstOrNull()?.let { model ->

            binding?.let {
                it.rvTransactions.adapter = TransactionsAdapter(requireContext(), model.transactions.toList())
                it.tvName.text = model.sku
                it.tvMovements.text = getString(R.string.detail_movements_count, model.transactions.size)
                it.tvTotal.text = getString(
                    R.string.detail_total_amount,
                    roundDoubleAmount(model.totalAmount),
                    DEFAULT_CURRENCY
                )

                it.llProductHeader.visibility = View.VISIBLE
                it.rvTransactions.visibility = View.VISIBLE
                it.tvError.visibility = View.GONE
            }
        } ?: run {
            onRequestFail(CodeErrors.DATA_ERROR)
        }
    }

    private fun onRequestFail(errorCode: Int) {
        binding?.let {
            it.llProductHeader.visibility = View.GONE
            it.rvTransactions.visibility = View.GONE
            it.tvError.visibility = View.VISIBLE
        }
    }

}