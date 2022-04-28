package com.example.newsportal.news

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.newsportal.MainActivity
import com.example.newsportal.Model.Articles
import com.example.newsportal.Model.Headlines
import com.example.newsportal.R
import com.littlemango.stacklayoutmanager.StackLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter : NewsAdapter
    private lateinit var newsList : ArrayList<Articles>
    var pageNumber = 1
    var totalResults = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        recyclerView = findViewById(R.id.recyclerview_news)
        val stackLayoutManger = StackLayoutManager(StackLayoutManager.ScrollOrientation.BOTTOM_TO_TOP)
        stackLayoutManger.setPagerMode(true)
        stackLayoutManger.setPagerFlingVelocity(3000)
        stackLayoutManger.setItemChangedListener(object:StackLayoutManager.ItemChangedListener{
            override fun onItemChanged(position: Int) {
                val container = findViewById<ConstraintLayout>(R.id.container_news)
                container.setBackgroundColor(Color.parseColor(ColorPicker.getColor()))
                if(totalResults>stackLayoutManger.itemCount && stackLayoutManger.getFirstVisibleItemPosition() >= stackLayoutManger.itemCount-5){
                    pageNumber++
                    getNews()
                }
            }
        })
        recyclerView.layoutManager = stackLayoutManger

        newsList = ArrayList()

        val intent = intent
        val is_checked = intent.getBooleanExtra("checkedStatus",true)

        val switch_news = findViewById<Switch>(R.id.switch_news)
        switch_news.isChecked = is_checked

        switch_news.setOnCheckedChangeListener{_,checked->
            if(!switch_news.isChecked){
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        getNews()
    }

    private fun getNews(){
        val call = NewsService.newsInstance.getHeadlinesNews("in",pageNumber)

        call.enqueue(object: Callback<Headlines> {
            override fun onResponse(call: Call<Headlines>, response: Response<Headlines>) {
                if(response.isSuccessful && response.body()?.articles != null){
                    newsList = response.body()!!.articles
                    totalResults = response.body()!!.totalResults.toInt()
                    newsAdapter = NewsAdapter(applicationContext,newsList)
                    recyclerView.adapter = newsAdapter
                }
            }

            override fun onFailure(call: Call<Headlines>, t: Throwable) {
                Toast.makeText(applicationContext, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}

object ColorPicker{
    val colors = arrayOf("#3685BC","#D36280","#E44F55","#FA8056","#818BCA","#7D659F","#51BAB3","#4FB66C","#E3AD17","#627991","#EF8EAD","#B5BFC6")
    var colorIndex = 0
    fun getColor() : String{
        colorIndex = (colorIndex+1) % colors.size
        return colors[colorIndex]
    }
}