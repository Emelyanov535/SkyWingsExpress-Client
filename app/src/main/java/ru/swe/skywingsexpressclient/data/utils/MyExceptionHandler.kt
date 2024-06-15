package ru.swe.skywingsexpressclient.data.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast

class MyExceptionHandler(private val context: Context) : Thread.UncaughtExceptionHandler {
    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, "An error occurred: ${throwable.message}", Toast.LENGTH_LONG).show()
        }
        Log.e("Писька попка", "Uncaught exception: ", throwable)
        try {
            Thread.sleep(1000) // Задержка для отображения Toast
        } catch (e: InterruptedException) {
            // Игнорировать
        }
    }
}
