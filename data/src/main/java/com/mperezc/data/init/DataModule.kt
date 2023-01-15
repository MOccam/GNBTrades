package com.mperezc.data.init

import com.mperezc.data.api.TradesConnectorService
import com.mperezc.data.source.RateDataSource
import com.mperezc.data.source.TransactionDataSource
import com.mperezc.data.source.remote.RateRemoteDataSource
import com.mperezc.data.source.remote.TransactionRemoteDataSource
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

@JvmField
val dataModule = module {
    singleOf(::TradesConnectorService)
    factoryOf(::RateRemoteDataSource) { bind<RateDataSource>()}
    factoryOf(::RateRemoteDataSource) { bind<RateDataSource>()}
    factoryOf(::TransactionRemoteDataSource) { bind<TransactionDataSource>()}
}