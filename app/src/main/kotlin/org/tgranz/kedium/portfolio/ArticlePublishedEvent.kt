package org.tgranz.kedium.portfolio

import org.tgranz.kedium.event.Event

data class ArticlePublishedEvent(private val article: Article) : Event {}
