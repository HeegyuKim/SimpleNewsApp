package kr.heegyu.simplenewsapp.android.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kr.heegyu.simplenewsapp.app.entity.News
import java.util.*

open class NewsModel
    : RealmObject {

    @PrimaryKey
    open var url: String = ""
    open var title: String = ""
    open var content: String = ""

    open var imageUrl: String = ""
    open var publishedDate: Date = Date()

    constructor() : super()

    constructor(news: News): super() {
        this.title = news.title
        this.content = news.content
        this.url = news.url
        this.imageUrl = news.imageUrl
        this.publishedDate = news.createdDate
    }

    constructor(title: String, content: String, url: String, imageUrl: String, publishedDate: Date) : super() {
        this.title = title
        this.content = content
        this.url = url
        this.imageUrl = imageUrl
        this.publishedDate = publishedDate
    }


    fun toNews(): News {
        return News(url, title, publishedDate, content, imageUrl, true)
    }

    override fun toString(): String {
        return "NewsModel(title='$title', content='$content', url='$url', imageUrl='$imageUrl')"
    }
}