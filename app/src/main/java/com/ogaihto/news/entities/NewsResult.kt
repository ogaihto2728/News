package com.ogaihto.news.entities

data class NewsResult(
    var status: String,
    var totalResults: Int,
    var articles: List<Article>
)