package com.fivestars.cloveroauthsample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fivestars.cloveroauthsample.MainActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn =
            findViewById<View>(R.id.button) as Button
        btn.setOnClickListener { // Starts intent to fetch OAuth 2.0 information
            val intent = Intent(applicationContext, WebViewActivity::class.java)
            startActivityForResult(intent, OAUTH_REQUEST_CODE)
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (requestCode == OAUTH_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {

            // Access data from the completed intent
            val token = data.getStringExtra(ACCESS_TOKEN_KEY)
            val merchantId =
                data.getStringExtra(MERCHANT_ID_KEY)
            val employeeId =
                data.getStringExtra(EMPLOYEE_ID_KEY)
            Toast.makeText(this@MainActivity, token, Toast.LENGTH_LONG).show()
            val btn =
                findViewById<View>(R.id.button) as Button
            btn.visibility = View.GONE
            val txtView = findViewById<View>(R.id.textView) as TextView
            txtView.text =
                "Access Token = $token\nMerchant Id = $merchantId\nEmployee Id = $employeeId"
        } else {
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val OAUTH_REQUEST_CODE = 0
        const val ACCESS_TOKEN_KEY = "access_token"
        const val MERCHANT_ID_KEY = "merchant_id"
        const val EMPLOYEE_ID_KEY = "employee_id"
    }
}