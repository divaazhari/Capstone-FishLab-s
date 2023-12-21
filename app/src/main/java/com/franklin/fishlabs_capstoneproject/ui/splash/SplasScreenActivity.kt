package com.franklin.fishlabs_capstoneproject.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.franklin.fishlabs_capstoneproject.R
import com.franklin.fishlabs_capstoneproject.ui.signin.SignInActivity

class SplasScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splas_screen)

        supportActionBar?.hide()
            Handler().postDelayed({
            val intent = Intent(this@SplasScreenActivity, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)

    }
}