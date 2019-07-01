package kr.heegyu.simplenewsapp.app

import kr.heegyu.simplenewsapp.app.repo.NewsRepository


interface SimpleNewsAppFactory {

    fun createNewsRepository(): NewsRepository

    fun close()
}