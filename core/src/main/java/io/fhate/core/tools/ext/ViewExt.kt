package io.fhate.core.tools.ext

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.SystemClock
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import io.fhate.core.R
import io.fhate.core.tools.Constants
import io.fhate.core.ui.base.BaseActivity

var lastClickTime: Long = 0

/* Multiple click safety (lock click for provided time) */
fun View.onBlockedClick(delay: Long = Constants.CLICK_MILLIS_LONG, action: () -> Unit) {
    setOnClickListener {
        if (SystemClock.elapsedRealtime() - lastClickTime >= delay) {
            lastClickTime = SystemClock.elapsedRealtime()
            action.invoke()
        }
    }
}

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.show(duration: Long = 500, onAnimationEnd: (() -> Unit)? = null) {
    if (isVisible()) {
        return
    }
    alpha = 0f
    visibility = View.VISIBLE
    animate()
        .alpha(1f)
        .setDuration(duration)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                onAnimationEnd?.invoke()
            }
        })
}
fun View.hide(toVisibility: Int = View.GONE, duration: Long = 500, onAnimationEnd: (() -> Unit)? = null) {
    if (!isVisible()) {
        return
    }
    alpha = 1f
    animate()
        .alpha(0f)
        .setDuration(duration)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                visibility = toVisibility
                onAnimationEnd?.invoke()
            }
        })
}

/* Load image using glide */
fun ImageView.loadFromUrl(url: String?,
                          onLoadingFinished: () -> Unit = {},
                          onLoadingError: () -> Unit = {}) {
    val listener = object : RequestListener<Drawable> {
        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
            onLoadingFinished.invoke()
            onLoadingError.invoke()
            return false
        }

        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            onLoadingFinished.invoke()
            return false
        }
    }
    Glide.with(this)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .error(R.drawable.undraw_not_found)
        .centerCrop()

        .listener(listener)
        .into(this)
}