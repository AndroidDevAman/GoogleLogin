package com.example.firebaseapplication

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.analytics.FirebaseAnalytics


class MainActivity : AppCompatActivity(), GoogleHelper.GoogleHelperCallback {
    private lateinit var googleHelper: GoogleHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        googleHelper = GoogleHelper(this, this)

        val crashButton = Button(this)
        crashButton.text = "Google Login"
        crashButton.setOnClickListener {
            if (hasNetwork()) {
                googleHelper.signIn()
            } else
                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
        }

        addContentView(crashButton, ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT))
    }

    override fun onSuccessGoogle(account: GoogleSignInAccount?) {
        initGoogleApi(account)
    }

    private fun initGoogleApi(account: GoogleSignInAccount?) {
        if (account == null) {
            return
        }
        Toast.makeText(this, account.givenName, Toast.LENGTH_SHORT).show()
    }

    override fun onErrorGoogle() {
        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
    }


    fun hasNetwork(): Boolean {
        return checkIfHasNetwork()
    }

    fun checkIfHasNetwork(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        googleHelper.onResult(requestCode, resultCode, data)
    }

}