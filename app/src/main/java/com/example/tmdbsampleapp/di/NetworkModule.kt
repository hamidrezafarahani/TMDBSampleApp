package com.example.tmdbsampleapp.di

import android.app.Application
import com.example.tmdbsampleapp.BuildConfig
import com.example.tmdbsampleapp.data.remote.TMDBService
import com.example.tmdbsampleapp.shared.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @LoggingInterceptor
    @Provides
    fun provideLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor { message -> Timber.tag("http-logger").d(message) }.apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }
    }

    @AuthInterceptor
    @Provides
    fun provideAuthInterceptor(): Interceptor {
        return Interceptor {
            val url = it.request().url.newBuilder().apply {
                addQueryParameter("language", "en-US")
            }.build()
            val request = it.request().newBuilder()
                .addHeader("Authorization", "Bearer ".plus(BuildConfig.TOKEN))
                .url(url)
                .build()
            it.proceed(request)
        }
    }

    @Provides
    fun provideOkHttpCache(app: Application): Cache {
        return Cache(app.cacheDir, 50_000_000)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @AuthInterceptor authInterceptor: Interceptor,
        @LoggingInterceptor loggingInterceptor: Interceptor,
        cache: Cache
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .callTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    fun provideRetrofit(
        client: dagger.Lazy<OkHttpClient>,
//        client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        callAdapterFactory: CallAdapter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .callFactory { request -> client.get().newCall(request) }
//            .client(client)
            .addCallAdapterFactory(callAdapterFactory)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideAppService(retrofit: Retrofit): TMDBService {
        return retrofit.create(TMDBService::class.java)
    }
}