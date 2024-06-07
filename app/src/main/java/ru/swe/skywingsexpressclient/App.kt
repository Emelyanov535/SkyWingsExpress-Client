package ru.swe.skywingsexpressclient

import android.app.Application
import ru.swe.skywingsexpressclient.di.AppContainer
import ru.swe.skywingsexpressclient.di.AppDataContainer

class App : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer()
    }
}