package com.github.mininalisticclient._di.data.modules

import com.github.mininalisticclient.data.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataDaoModule = module {
    single { AppDatabase.getDatabase(androidContext()) }
    single { get<AppDatabase>().userDao() }
    single { get<AppDatabase>().repoDao() }
}
