package io.fhate.network.executor

import android.util.Log

interface RequestExecutor {

    companion object {
        const val TAG = "TAG:RequestExecutor"
    }

    fun log(message: String) {
        Log.d(TAG, message)
    }

}