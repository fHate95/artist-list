package io.fhate.artist_list.ui.start

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.fhate.artist_list.ui.home.HomeActivity

/* StartActivity for start routing if needed.
 This Activity shows SplashTheme while App initializing with no static time */
class StartActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

}