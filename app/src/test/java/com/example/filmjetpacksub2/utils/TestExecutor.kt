package com.example.filmjetpacksub2.utils

import java.util.concurrent.Executor

internal class TestExecutor : Executor {

    override fun execute(runnable: Runnable) {
        runnable.run()
    }
}