package ru.swe.skywingsexpressclient

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import ru.swe.skywingsexpressclient.data.utils.MyExceptionHandler
import ru.swe.skywingsexpressclient.di.AppContainer
import ru.swe.skywingsexpressclient.di.AppDataContainer

class App : Application() {
    lateinit var container: AppContainer

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
        Thread.setDefaultUncaughtExceptionHandler(MyExceptionHandler(this))
    }
}