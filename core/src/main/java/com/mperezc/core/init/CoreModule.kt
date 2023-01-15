package com.mperezc.core.init

import com.mperezc.core.middleware.IMiddlewareService
import com.mperezc.core.middleware.MiddlewareService
import com.mperezc.core.middleware.RetrofitConfig
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

@JvmField
val coreModule = module {
    singleOf(::RetrofitConfig)
    singleOf(::MiddlewareService) { bind<IMiddlewareService>() }
}
