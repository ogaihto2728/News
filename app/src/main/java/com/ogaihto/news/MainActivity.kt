package com.ogaihto.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ogaihto.news.entities.Article
import com.ogaihto.news.entities.NewsResult
import com.ogaihto.news.services.NewsService
import com.ogaihto.news.ui.ArticleAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var retrofit: Retrofit
    lateinit var service: NewsService
    lateinit var adapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retrofit = Retrofit.Builder().baseUrl("https://newsapi.org/v2/").addConverterFactory(GsonConverterFactory.create()).build()
        service = retrofit.create(NewsService::class.java)

        doSearch()

        bt_search.setOnClickListener {
            doSearch(in_terms.text.toString())
        }

    }

    fun doSearch(terms: String = "", country: String = "br") {
        service.searchNews(terms, country).enqueue(object : Callback<NewsResult> {
            override fun onResponse(call: Call<NewsResult>, response: Response<NewsResult>) {
                val result = response.body()
                if (result != null) {
                    if (result.status == "ok" && result.totalResults > 0)
                        fillThatList(result.articles)
                }
            }

            override fun onFailure(call: Call<NewsResult>, t: Throwable) {
                println("oops")
            }

        })
    }

    fun fillThatList(articles: List<Article>) {
        adapter = ArticleAdapter(articles.toMutableList())
        listArticles.adapter = adapter

        listArticles.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

    }
}
