package kr.heegyu.simplenewsapp.android.retrofit

data class NewsSource(
    var id: String = "",
    var name: String = ""
)

data class NewsArticle(
    var source: NewsSource,
    var author: String?,
    var title: String,
    var description: String,
    var url: String,
    var urlToImage: String,
    var publishedAt: String,
    var content: String
)

data class NewsResponse (
    var status: String = "",
    var code: Int? = null,
    var message: String? = null,
    var totalResult: Int = 0,
    var articles: List<NewsArticle> = emptyList()
)