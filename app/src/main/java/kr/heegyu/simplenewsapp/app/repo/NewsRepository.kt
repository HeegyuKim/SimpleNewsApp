package kr.heegyu.simplenewsapp.app.repo

import kr.heegyu.simplenewsapp.app.entity.News


interface NewsRepository : Repository {

    fun search(query: String,
            category: String,
            page: Int,
            pageSize: Int = 20
            ): List<News>

    fun addNews(news: News)

    fun updateNews(news: News)

    fun deleteNews(id: String)

    fun getFavorites(page: Int, pageSize: Int = 20): List<News>
}