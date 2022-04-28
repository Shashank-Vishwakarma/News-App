package com.example.newsportal.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.example.newsportal.R

class NewsdetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newsdetail)

        val progressbar : ProgressBar = findViewById(R.id.progressbar_news_detail_activity)

        val intent = intent.getStringExtra("url")
        val webview = findViewById<WebView>(R.id.webview_news_details_carousel)

        webview.settings.javaScriptEnabled = true
        webview.settings.loadsImagesAutomatically = true
        webview.settings.domStorageEnabled = true
        webview.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                progressbar.visibility = View.INVISIBLE
                webview.visibility = View.VISIBLE
            }
        }
        webview.loadUrl(intent!!)
    }
}