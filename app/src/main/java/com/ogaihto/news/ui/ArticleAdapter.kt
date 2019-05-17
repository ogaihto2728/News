package com.ogaihto.news.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ogaihto.news.entities.Article
import kotlinx.android.synthetic.main.article_card.view.*
import android.text.format.DateUtils
import com.ogaihto.news.R
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*


class ArticleAdapter(var articles: MutableList<Article>): RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {
    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.fillCard(articles[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        println(parent.context)
        return ArticleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.article_card, parent, false))
    }


    inner class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun fillCard(article: Article) {
            itemView.tx_author.setText(article.author)
            itemView.tx_preview.setText(article.description)
            itemView.tx_source.setText(article.source.name)
            itemView.tx_title.setText(article.title)

            itemView.tx_datetime.setText(article.publishedAt)

            Picasso.get().load(article.urlToImage).into(itemView.im_article)

            itemView.bt_more.setOnClickListener {
                itemView.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(article.url)))
            }
        }
    }
}