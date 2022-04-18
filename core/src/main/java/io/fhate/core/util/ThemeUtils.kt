package io.fhate.core.util

import android.app.Activity
import android.os.Build
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

object ThemeUtils {

    fun applySystemBarsColor(activity: Activity, @ColorRes statusBarColor: Int, @ColorRes navigationBarColor: Int) {
        setStatusBarColor(activity, statusBarColor)
        setNavigationBarColor(activity, navigationBarColor)
    }

    private fun setStatusBarColor(activity: Activity, @ColorRes colorId: Int) {
        if (Build.VERSION.SDK_INT >= 23) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.statusBarColor = ContextCompat.getColor(activity, colorId)
        }
    }

    private fun setNavigationBarColor(activity: Activity, @ColorRes colorId: Int) {
        if (Build.VERSION.SDK_INT >= 27) {
            activity.window.navigationBarColor = ContextCompat.getColor(activity, colorId)
        }
    }


}