package com.github.mininalisticclient._di.data.modules

import com.github.mininalisticclient.BuildConfig
import com.github.mininalisticclient.data.network.GithubApis
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataNetworkModule = module {
    single { Gson() }

    single {
        val timeoutTime = 1L
        val builder = OkHttpClient.Builder()
            .readTimeout(timeoutTime, TimeUnit.MINUTES)
            .connectTimeout(timeoutTime, TimeUnit.MINUTES)
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }
        builder.build()
    }

    single {
        val baseURL = BuildConfig.GITHUB_API_URL
        Retrofit.Builder()
            .baseUrl(baseURL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    single { get<Retrofit>().create(GithubApis::class.java) }
}
