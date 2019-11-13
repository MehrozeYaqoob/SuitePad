package com.suitepad.datasource.executor

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by Mehroze on 11/9/2019.
 */
class AppExecutor constructor(private val diskIO: Executor, val networkIO: Executor, val mainThread: Executor){

    constructor(): this(
        Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3),
        MainThreadExecutor())

    private class MainThreadExecutor: Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

    private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

    fun ioThread(f : () -> Unit) {
        IO_EXECUTOR.execute(f)
    }
}