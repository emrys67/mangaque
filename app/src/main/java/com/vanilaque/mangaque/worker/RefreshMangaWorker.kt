package com.vanilaque.mangaque.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.vanilaque.mangaque.MangaUseCase
import com.vanilaque.mangaque.service.PrefManager
import com.vanilaque.mangareader.data.repository.MangaRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class RefreshMangaWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val useCase: MangaUseCase
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        try {
            useCase.syncManga()
            return Result.success()
        } catch (e: Exception) {
            Log.e("", "reslut fail with $e")
            return Result.retry()
        }
    }
}