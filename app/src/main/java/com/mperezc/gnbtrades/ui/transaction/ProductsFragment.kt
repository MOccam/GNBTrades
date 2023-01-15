package com.mperezc.gnbtrades.ui.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mperezc.domain.model.ProductModel
import com.mperezc.gnbtrades.R
import com.mperezc.gnbtrades.databinding.FragmentTransactionBinding
import com.mperezc.gnbtrades.ui.Error
import com.mperezc.gnbtrades.ui.Loading
import com.mperezc.gnbtrades.ui.Success
import com.mperezc.gnbtrades.ui.UiConstants
import com.mperezc.gnbtrades.ui.transaction.adapter.ProductsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProductsFragment : Fragment() {

    private var binding: FragmentTransactionBinding? = null
    private val viewModel: ProductsViewModel by viewModel()

    private val productsAdapter = ProductsAdapter {
        val bundle = bundleOf(UiConstants.NAVIGATION_PRODUCT_KEY to it.sku)
        findNavController().navigate(R.id.action_productsFragment_to_detailFragment, bundle)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionBinding.inflate(inflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSwipeRefresh()
        setupAdapter()
        setupObservers()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        //Prevent memory leak
        binding = null
    }

    private fun setupSwipeRefresh() {
        binding?.let{
            with(it.swipeContainer) {
                setOnRefreshListener {
                    viewModel.getTransactionsList()
                }

                setColorSchemeResources(
                    R.color.md_theme_light_primary,
                    R.color.md_theme_light_secondary,
                    R.color.md_theme_light_tertiary,
                )
            }
        }
    }

    private fun setupAdapter() {
        binding?.let {
            with(it.rvProducts) {
                val llm = LinearLayoutManager(requireContext())
                llm.orientation = LinearLayoutManager.VERTICAL
                layoutManager = llm

                //Set adapter
                adapter = productsAdapter

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
        binding?.swipeContainer?.isRefreshing = true
        binding?.rvProducts?.visibility = View.GONE
        binding?.tvError?.visibility = View.GONE
    }

    private fun onGetProducts(list: List<ProductModel>) {
        binding?.swipeContainer?.isRefreshing = false
        productsAdapter.refresh(list)
        binding?.rvProducts?.visibility = View.VISIBLE
        binding?.tvError?.visibility = View.GONE
    }

    private fun onRequestFail(errorCode: Int) {
        binding?.swipeContainer?.isRefreshing = false
        binding?.rvProducts?.visibility = View.GONE
        binding?.tvError?.visibility = View.VISIBLE
    }

}