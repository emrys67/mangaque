package com.vanilaque.mangaque

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.startup.Initializer
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class WorkManagerInitializer : Initializer<WorkManager> {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun create(@ApplicationContext context: Context): WorkManager {
        val configuration = Configuration.Builder()
            .setWorkerFactory(workerFactory)  // Set Hilt's worker factory
            .build()
        WorkManager.initialize(context, configuration)
        Log.d("Hilt Init", "WorkManager initialized by Hilt this time")
        return WorkManager.getInstance(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}