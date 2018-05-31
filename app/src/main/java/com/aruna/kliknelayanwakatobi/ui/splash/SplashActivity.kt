package com.aruna.kliknelayanwakatobi.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.aruna.kliknelayanwakatobi.R
import com.aruna.kliknelayanwakatobi.tools.SPLASH_DELAY
import com.aruna.kliknelayanwakatobi.ui.login.view.LoginActivity

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({ gotoLogin() }, SPLASH_DELAY)
    }

    private fun gotoLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
