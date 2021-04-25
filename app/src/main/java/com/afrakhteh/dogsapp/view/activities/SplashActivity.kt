package com.afrakhteh.dogsapp.view.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.afrakhteh.dogsapp.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    lateinit var aviLoad: Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)

        setLoading()
    }

    private fun setLoading() {
        aviLoad = startAnim()
        val handle: Handler = Handler()
        handle.postDelayed(Runnable {
            aviLoad = stopAnim()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)

    }

    fun startAnim() {
        avi.show()
    }

    fun stopAnim() {
        avi.hide()
    }
}