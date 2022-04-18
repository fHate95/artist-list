package io.fhate.core.ui.base

import android.content.Context
import android.graphics.Rect
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.util.*

abstract class BaseActivity: AppCompatActivity(), View.OnTouchListener {

    private var touchTime: Long = 0

    private var connectivityManager: ConnectivityManager? = null
    private var networkRequest: NetworkRequest? = null
    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    /* Register network callback to catch network enabled/disabled actions */
    fun registerNetworkCallback(networkCallback: ConnectivityManager.NetworkCallback) {
        this.networkCallback = networkCallback
        networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    override fun onResume() {
        super.onResume()
        if (networkRequest != null && networkCallback != null) {
            connectivityManager?.registerNetworkCallback(networkRequest!!, networkCallback!!)
        }
    }

    override fun onPause() {
        super.onPause()
        if (networkRequest != null && networkCallback != null) {
            connectivityManager?.unregisterNetworkCallback(networkCallback!!)
        }
    }

    /* Hide soft input if EditText focused
     Could be used on root (background) layouts with layout.setOnTouchListener(this) */
    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        if (view == null || event == null) {
            return false
        }
        if (event.action == MotionEvent.ACTION_DOWN) {
            touchTime = SystemClock.elapsedRealtime()
            return true
        }
        // If short tap detected..
        if (event.action == MotionEvent.ACTION_UP) {
            val upTime = SystemClock.elapsedRealtime()
            if (upTime - touchTime < 150) {
                val v = currentFocus
                // ..and EditText focused: hide soft input
                if (v is EditText) {
                    val outRect = Rect()
                    v.getGlobalVisibleRect(outRect)
                    if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                        v.clearFocus()
                        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                    }
                }
                view.performClick()
            }
            return super.onTouchEvent(event)
        }
        return super.onTouchEvent(event)
    }

}