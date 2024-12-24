package com.bruno13palhano.mvi.ui.screens.other

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.bruno13palhano.mvi.repository.Repository
import com.bruno13palhano.mvi.ui.di.Dispatcher
import com.bruno13palhano.mvi.ui.di.MVIDispatchers.IO
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

@HiltWorker
internal class SomeWork @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val repository: Repository,
    @Dispatcher(IO) private val dispatcher: CoroutineDispatcher,
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return withContext(dispatcher) {
            if (repository.fakeWork()) {
                Result.success()
            } else {
                Result.retry()
            }
        }
    }

    companion object {
        fun startFakeWork() =
            OneTimeWorkRequestBuilder<SomeWork>()
                .setConstraints(
                    Constraints
                        .Builder()
                        .setRequiresBatteryNotLow(true)
                        .build()
                ).build()
    }
}