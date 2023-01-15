package com.mperezc.gnbtrades.init

import com.mperezc.gnbtrades.ui.transaction.ProductsViewModel
import com.mperezc.gnbtrades.ui.detail.DetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

@JvmField
val uiModule = module {
    viewModelOf(::ProductsViewModel)
    viewModelOf(::DetailViewModel)
}