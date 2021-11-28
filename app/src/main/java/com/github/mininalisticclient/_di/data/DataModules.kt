package com.github.mininalisticclient._di.data

import com.github.mininalisticclient._di.data.modules.dataDaoModule
import com.github.mininalisticclient._di.data.modules.dataNetworkModule
import com.github.mininalisticclient._di.data.modules.dataRepositoryModule

val dataModules = listOf(
    dataNetworkModule,
    dataRepositoryModule,
    dataDaoModule
)
