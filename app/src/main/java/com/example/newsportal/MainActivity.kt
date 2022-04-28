package com.example.newsportal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newsportal.Model.Articles
import com.example.newsportal.Model.Headlines
import com.example.newsportal.news.NewsActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

//   https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=50cc17c391fd4051b486462dd1d1a803
class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter :NewsRecyclerViewAdapter
    private lateinit var articles : ArrayList<Articles>
    private lateinit var refreshlayout : SwipeRefreshLayout
    private lateinit var search_edittext : EditText
    private lateinit var search_imageview : ImageView
    companion object{
        const val API_KEY = "50cc17c391fd4051b486462dd1d1a803"

        fun getCountry() : String{
            val locale : Locale = Locale.getDefault()
            val country = locale.country
            return country.lowercase()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val switch_main = findViewById<Switch>(R.id.switch_main)
        switch_main.setOnCheckedChangeListener {_,checked->
            val is_checked = switch_main.isChecked
            if(is_checked){
                val intent = Intent(this,NewsActivity::class.java)
                intent.putExtra("checkedStatus",is_checked)
                startActivity(intent)
                finish()
            }
        }


        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        articles = ArrayList()

        val country = getCountry()
        retrieveJson("",country, API_KEY)

        refreshlayout = findViewById(R.id.refreshlayout)
        refreshlayout.setOnRefreshListener {
            retrieveJson("",country, API_KEY)
        }

        search_imageview = findViewById(R.id.search_button_main_activity)
        search_imageview.setOnClickListener {
            val query = search_edittext.text.toString()
            if(query != ""){
                retrieveJson(query,country, API_KEY)
                refreshlayout.setOnRefreshListener {
                    retrieveJson(query,country, API_KEY)
                }
            } else{
                retrieveJson("",country, API_KEY)
                refreshlayout.setOnRefreshListener {
                    retrieveJson("",country, API_KEY)
                }
            }
        }
    }

    fun retrieveJson(query:String ,country:String,apiKey:String){

        var call : Call<Headlines>? = null
        search_edittext = findViewById(R.id.search_edittext_main_activity)
        var search_query = search_edittext.text.toString()
        if(!search_query.equals("")){
            call = ApiClient.getInstance().getApi().getSpecificData(query,apiKey)
        } else{
            call = ApiClient.getInstance().getApi().getHeadlines(country,apiKey)
        }
        call?.enqueue(object:Callback<Headlines>{
            override fun onResponse(call: Call<Headlines>, response: Response<Headlines>) {
                if(response.isSuccessful && response.body()?.articles != null){
                    refreshlayout.isRefreshing = false

                    articles.clear()
                    articles = response.body()!!.articles
                    adapter = NewsRecyclerViewAdapter(applicationContext,articles)
                    recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<Headlines>, t: Throwable) {
                refreshlayout.isRefreshing = false
                Toast.makeText(this@MainActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }
}