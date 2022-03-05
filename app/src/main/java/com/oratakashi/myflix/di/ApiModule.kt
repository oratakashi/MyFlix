package com.oratakashi.myflix.di

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.oratakashi.myflix.BuildConfig
import com.oratakashi.myflix.Config
import com.oratakashi.myflix.data.web.TmdbApi
import com.oratakashi.myflix.data.web.TmdbApiClient
import com.oratakashi.viewbinding.core.tools.retrofit.createOkHttpClient
import com.oratakashi.viewbinding.core.tools.retrofit.createService
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val apiModule = module {
    single {
        ChuckerCollector(
            context = androidContext(),
            showNotification = BuildConfig.DEBUG,
            retentionPeriod = RetentionManager.Period.ONE_DAY
        )
    }

    single {
        ChuckerInterceptor.Builder(androidContext())
            .apply {
                collector(get())
                maxContentLength(250_000L)
                alwaysReadResponseBody(false)
            }
            .build()
    }

    single {
        Interceptor { chain ->
            var request: Request = chain.request()
            val url: HttpUrl = request.url.newBuilder()
                .addQueryParameter("api_key", Config.key)
                .addQueryParameter("language", Config.lang)
                .build()
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }
    }

    single {
        createOkHttpClient(
            arrayOf(
                get<ChuckerInterceptor>(),
                get()
            ),
            null,
            null,
            BuildConfig.DEBUG
        )
    }

    single {
        createService(
            TmdbApiClient::class.java,
            get(),
            BuildConfig.BASE_URL
        )
    }

    single { TmdbApi(get()) }
}