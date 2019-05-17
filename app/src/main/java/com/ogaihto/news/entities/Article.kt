package com.ogaihto.news.entities

data class Article(
    var source: Source,
    var author: String?,
    var title: String,
    var description: String,
    var url: String,
    var urlToImage: String,
    var publishedAt: String)
