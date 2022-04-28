package com.example.newsportal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.squareup.picasso.Picasso

class DetailsActivity : AppCompatActivity() {
    private lateinit var title_textview : TextView
    private lateinit var source_textview : TextView
    private lateinit var date_textview : TextView
    private lateinit var description_textview : TextView
    private lateinit var imageview : ImageView
    private lateinit var webview : WebView
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // get the ids
        title_textview = findViewById(R.id.title_textView_details)
        source_textview = findViewById(R.id.source_textView_details)
        date_textview = findViewById(R.id.date_textview_details)
        description_textview = findViewById(R.id.description_details)
        imageview = findViewById(R.id.news_image_details)
        webview = findViewById(R.id.webview_details)
        progressBar = findViewById(R.id.loader_details)

        progressBar.visibility = View.VISIBLE

        val intent = intent
        val title = intent.getStringExtra("title")
        val source = intent.getStringExtra("source")
        val date = intent.getStringExtra("date")
        val urlToImage = intent.getStringExtra("urlToImage")
        val url = intent.getStringExtra("url")
        val description = intent.getStringExtra("description")

        title_textview.text = title
        source_textview.text = source
        date_textview.text = date
        description_textview.text = description
        Picasso.get().load(urlToImage).into(imageview)

        webview.settings.domStorageEnabled = true
        webview.settings.javaScriptEnabled = true
        webview.settings.loadsImagesAutomatically = true
        webview.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webview.webViewClient = WebViewClient()
        webview.loadUrl(url!!)
        if(webview.isShown){
            progressBar.visibility = View.INVISIBLE
        }
    }
}