package kr.heegyu.simplenewsapp.app.entity

import java.util.*


data class News(
    var url: String,
    var title: String,
    var createdDate: Date,
    var content: String,
    var imageUrl: String,
    var isFavorite: Boolean
) {

    override fun equals(other: Any?): Boolean {
        return if(other is News) other.url == url else false
    }
}