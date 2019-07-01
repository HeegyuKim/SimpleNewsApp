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

}