package com.dreyesyho.myapplication.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dreyesyho.myapplication.data.WeathersRepository

class WeatherSyncWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val weathersRepository: WeathersRepository
): CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            weathersRepository.fetchRemoteWeathers()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

}