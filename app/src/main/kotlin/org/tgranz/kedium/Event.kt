package org.tgranz.kedium

data class ArticlePublishedEvent(private val article: Article) : Event {}

interface Event
