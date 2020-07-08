package com.fivestars.cloveroauthsample

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient

/**
 * Created by zach on 6/19/14.
 */
class WebViewActivity : Activity() {
    private var webView: WebView? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_webview)

        // The URL that will fetch the Access Token, Merchant ID, and Employee ID
        val url = "https://clover.com/oauth/authorize" +
                "?client_id=" + Config.APP_ID +
                "&response_type=token" +
                "&redirect_uri=" + Config.APP_DOMAIN

        // Creates the WebView
        webView = findViewById<View>(R.id.webView) as WebView
        webView!!.settings.javaScriptEnabled = true
        webView!!.webViewClient = object : WebViewClient() {
            override fun onPageStarted(
                view: WebView,
                url: String,
                favicon: Bitmap
            ) {
                // Parses the fetched URL
                val accessTokenFragment = "#access_token="
                val merchantIdFragment = "&merchant_id="
                val employeeIdFragment = "&employee_id="
                val accessTokenStart = url.indexOf(accessTokenFragment)
                val merchantIdStart = url.indexOf(merchantIdFragment)
                val employeeIdStart = url.indexOf(employeeIdFragment)
                if (accessTokenStart > -1) {
                    val accessToken = url.substring(
                        accessTokenStart + accessTokenFragment.length,
                        merchantIdStart
                    )
                    val merchantId =
                        url.substring(merchantIdStart + merchantIdFragment.length, employeeIdStart)
                    val employeeId =
                        url.substring(employeeIdStart + employeeIdFragment.length, url.length)

                    // Sends the info back to the MainActivity
                    val output = Intent()
                    output.putExtra(MainActivity.Companion.ACCESS_TOKEN_KEY, accessToken)
                    output.putExtra(MainActivity.Companion.MERCHANT_ID_KEY, merchantId)
                    output.putExtra(MainActivity.Companion.EMPLOYEE_ID_KEY, employeeId)
                    setResult(RESULT_OK, output)
                    finish()
                }
            }
        }
        // Loads the WebView
        webView!!.loadUrl(url)
    }
}