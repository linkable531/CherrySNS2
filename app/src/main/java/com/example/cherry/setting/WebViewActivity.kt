package com.example.cherry.setting
import com.example.cherry.R
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class WebViewActivity : AppCompatActivity() {
    private lateinit var mWebView: WebView // 웹뷰 선언
    private lateinit var mWebSettings: WebSettings // 웹뷰 세팅

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        mWebView = findViewById(R.id.webView)
        mWebView.webViewClient = WebViewClient()

        mWebSettings = mWebView.settings
        mWebSettings.javaScriptEnabled = true
        mWebSettings.loadWithOverviewMode = true
        mWebSettings.useWideViewPort = true
        mWebSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        mWebSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        mWebSettings.domStorageEnabled = true

        // Intent로 전달받은 URL 로딩
        val webUrl = intent.getStringExtra("WEB_URL")
        if (!webUrl.isNullOrBlank()) {
            mWebView.loadUrl(webUrl)
        }
    }
}