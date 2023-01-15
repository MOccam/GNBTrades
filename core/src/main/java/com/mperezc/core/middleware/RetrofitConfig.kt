package com.mperezc.core.middleware

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class RetrofitConfig {

    companion object {
        private const val TIME_OUT: Long = 20
    }

    private var builder: OkHttpClient.Builder? = null

    init {
        builder = OkHttpClient.Builder()
        builder?.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        builder?.readTimeout(TIME_OUT, TimeUnit.SECONDS)
    }

    open fun getRetrofit(baseUrl: String): Retrofit {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())

        // Only for debug
        //val interceptor = HttpLoggingInterceptor()
        //interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        //val client: OkHttpClient = builder!!.addInterceptor(interceptor).build()
        //retrofitBuilder.client(client)


        return retrofitBuilder.build()
    }
}