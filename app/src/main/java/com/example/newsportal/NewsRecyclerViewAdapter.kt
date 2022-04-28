package com.example.newsportal

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsportal.Model.Articles
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList

class NewsRecyclerViewAdapter(val context:Context,val newsArrayList: ArrayList<Articles>) : RecyclerView.Adapter<NewsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_list_item,parent,false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = newsArrayList[position]
        holder.title_textview.text = currentItem.title
        holder.source_textview.text = currentItem.source?.name
        holder.date_textview.text = currentItem.publishedAt

        Picasso.get().load(currentItem.urlToImage).into(holder.news_imageview)

        holder.cardview.setOnClickListener {
            val intent = Intent(context,DetailsActivity::class.java)
            intent.apply {
                putExtra("title",currentItem.title)
                putExtra("source",currentItem.source?.name)
                putExtra("date",currentItem.publishedAt)
                putExtra("urlToImage",currentItem.urlToImage)
                putExtra("url",currentItem.url)
                putExtra("description",currentItem.description)
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return newsArrayList.size
    }
}

class NewsViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
    val news_imageview = itemView.findViewById<ImageView>(R.id.news_image)
    val title_textview = itemView.findViewById<TextView>(R.id.title_textView)
    val source_textview = itemView.findViewById<TextView>(R.id.source_textView)
    val date_textview = itemView.findViewById<TextView>(R.id.date_textview)
    val cardview = itemView.findViewById<CardView>(R.id.cardView)
}