package com.mperezc.gnbtrades

import com.mperezc.core.init.coreModule
import com.mperezc.data.init.dataModule
import com.mperezc.domain.init.domainModule
import com.mperezc.gnbtrades.init.uiModule
import org.koin.core.module.Module

val appModules = listOf<Module>(
    uiModule,
    domainModule,
    dataModule,
    coreModule
)