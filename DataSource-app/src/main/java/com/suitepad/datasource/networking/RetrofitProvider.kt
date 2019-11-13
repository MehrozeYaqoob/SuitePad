package com.suitepad.datasource.networking

/**
 * Created by Mehroze on 11/9/2019.
 */
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.suitepad.datasource.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {
    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(OkhttpClient.client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> createAPI(service: Class<T>): T {
        return retrofit.create(service)
    }
}