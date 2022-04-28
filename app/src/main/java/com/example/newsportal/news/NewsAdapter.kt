package com.example.newsportal.news

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsportal.Model.Articles
import com.example.newsportal.Model.Headlines
import com.example.newsportal.R
import com.squareup.picasso.Picasso

class NewsAdapter(val context:Context,val articles:ArrayList<Articles>):RecyclerView.Adapter<NewsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item,parent,false)
        return NewsHolder(view)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val currentItem = articles[position]
        holder.title_textview.text = currentItem.title
        holder.desc_textview.text = currentItem.description
        Picasso.get().load(currentItem.urlToImage).into(holder.news_imageview)

        holder.linearlayout.setOnClickListener {
            val intent = Intent(context,NewsdetailActivity::class.java)
            intent.putExtra("url",currentItem.url)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }
}


class NewsHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
    val title_textview = itemView.findViewById<TextView>(R.id.title_news)
    val desc_textview = itemView.findViewById<TextView>(R.id.description_news)
    val news_imageview = itemView.findViewById<ImageView>(R.id.image_news)
    val linearlayout = itemView.findViewById<LinearLayout>(R.id.linearlayout_news_item)
}