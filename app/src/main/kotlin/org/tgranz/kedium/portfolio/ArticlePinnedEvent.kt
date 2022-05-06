package org.tgranz.kedium.portfolio

import org.tgranz.kedium.event.Event

class ArticlePinnedEvent(private val article: Article) : Event {}
