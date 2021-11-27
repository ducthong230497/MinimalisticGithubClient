package com.github.mininalisticclient._di.data

import com.github.mininalisticclient._di.data.modules.dataNetworkModule
import com.github.mininalisticclient._di.data.modules.dataRepositoryModule
import org.koin.core.module.Module

val dataModules = listOf<Module>(
    dataNetworkModule,
    dataRepositoryModule
)
