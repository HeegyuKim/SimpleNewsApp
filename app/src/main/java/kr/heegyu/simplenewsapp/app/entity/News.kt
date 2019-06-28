package kr.heegyu.simplenewsapp.app.entity

import java.util.*


data class News(
    var id: String,
    var title: String,
    var createdDate: Date,
    var content: String,
    var isFavorite: Boolean
) {

}