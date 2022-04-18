package io.fhate.core.tools.ext

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper

/* Execute action after provided delay */
fun delayAction(delayMillis: Long = 800, action: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed({
        try {
            action.invoke()
        } catch (ignored: Exception) {}
    }, delayMillis)
}

/* Open URL intent */
fun Context.openUrl(url: String) {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}