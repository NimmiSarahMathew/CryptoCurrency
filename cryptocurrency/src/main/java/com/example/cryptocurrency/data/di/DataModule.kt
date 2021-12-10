package com.example.cryptocurrency.data.di

import android.content.Context
import com.example.cryptocurrency.CoroutinesQualifiers
import com.example.cryptocurrency.data.model.CAPCOIN_ENDPOINT_HOST
import com.example.cryptocurrency.data.model.CoincapService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    @Singleton
    fun provideCoincapService(@ApplicationContext context: Context): CoincapService {
        val cacheSize = (10 * 1024 * 1024).toLong() //10 MB
        val myCache = Cache(context.cacheDir, cacheSize)

        //online cache interceptor
        val networkCacheInterceptor = Interceptor { chain ->
            val response = chain.proceed(chain.request())

            var cacheControl = CacheControl.Builder()
                .maxAge(5, TimeUnit.MINUTES)
                .build()

            response.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .build()
        }

        //custom http client for response cache
        val httpClient = OkHttpClient.Builder()
            .cache(myCache)
            .addNetworkInterceptor(networkCacheInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(CAPCOIN_ENDPOINT_HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create(CoincapService::class.java)
    }

    @CoroutinesQualifiers.DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @CoroutinesQualifiers.IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @CoroutinesQualifiers.MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main


}