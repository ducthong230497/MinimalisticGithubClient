package com.github.minimalisticclient._di.data

import com.github.minimalisticclient._di.data.modules.dataDaoModule
import com.github.minimalisticclient._di.data.modules.dataNetworkModule
import com.github.minimalisticclient._di.data.modules.dataRepositoryModule

val dataModules = listOf(
    dataNetworkModule,
    dataRepositoryModule,
    dataDaoModule
)
