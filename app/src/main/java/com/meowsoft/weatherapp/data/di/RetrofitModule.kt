package com.meowsoft.weatherapp.data.di

import com.meowsoft.weatherapp.BuildConfig
import com.meowsoft.weatherapp.data.remote.OpenMeteoAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    @Singleton
    fun providesRetrofit(
        okHttpClient: OkHttpClient
    ) = Retrofit
        .Builder()
        .client(okHttpClient)
        .baseUrl("https://api.open-meteo.com")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providesLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

    @Provides
    @Singleton
    fun providesOkHttp(
        loggingInterceptor: HttpLoggingInterceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun providesOpenMeteoAPI(
        retrofit: Retrofit
    ) = retrofit.create(OpenMeteoAPI::class.java)
}
