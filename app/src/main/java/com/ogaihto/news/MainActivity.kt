package com.ogaihto.news

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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
    lateinit var locale: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retrofit = Retrofit.Builder().baseUrl("https://newsapi.org/v2/").addConverterFactory(GsonConverterFactory.create(
            GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
        )).build()
        service = retrofit.create(NewsService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            locale = resources.configuration.locales[0].country
        else
            locale = resources.configuration.locale.country

        doSearch("", locale)

        bt_search.setOnClickListener {
            doSearch(in_terms.text.toString(), locale)
        }

    }

    fun doSearch(terms: String = "", country: String = "us") {
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
