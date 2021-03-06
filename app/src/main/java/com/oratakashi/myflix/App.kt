package com.oratakashi.myflix

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.oratakashi.myflix.di.apiModule
import com.oratakashi.myflix.di.databaseModule
import com.oratakashi.myflix.di.reqresModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_YES
        )
        startKoin {
            androidContext(applicationContext)
            modules(
                listOf(
                    apiModule,
                    databaseModule,
                    reqresModule
                )
            )
        }
    }
}