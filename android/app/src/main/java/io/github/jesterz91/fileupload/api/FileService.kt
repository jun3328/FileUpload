package io.github.jesterz91.fileupload.api

import io.github.jesterz91.fileupload.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object FileService {

    private const val BASE_URL = "http://192.168.0.10:3000/"

    private val interceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BASIC
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    val service: FileApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(FileApi::class.java)
    }
}