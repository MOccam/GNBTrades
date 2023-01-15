package com.mperezc.domain.init

import com.mperezc.domain.usecases.ProductUseCase
import com.mperezc.domain.usecases.RateUseCase
import com.mperezc.domain.usecases.TransactionUseCase
import com.mperezc.domain.usecases.impl.RateUseCaseImpl
import com.mperezc.domain.usecases.impl.TransactionUseCaseImpl
import com.mperezc.domain.usecases.impl.ProductUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

@JvmField
val domainModule = module {
    factoryOf(::TransactionUseCaseImpl) { bind<TransactionUseCase>() }
    factoryOf(::RateUseCaseImpl) { bind<RateUseCase>() }
    singleOf(::ProductUseCaseImpl) { bind<ProductUseCase>() }
}
