package org.tgranz.kedium

import io.kotest.core.spec.style.FeatureSpec
import org.tgranz.kedium.fakes.FakeEmailSenderPolicy

class PublishedArticlesAcceptanceTest : FeatureSpec({
    val emailSenderPolicy = FakeEmailSenderPolicy()
    val eventBus = EventBus().apply { subscribe(emailSenderPolicy) }
    val app = TestApplication(eventBus)

    xfeature("publish Article in Portfolio") {
        xscenario("quota exceeded") {
        }

        scenario("article published event emitted") {
            emailSenderPolicy.addEmailReceivers("test1@t.com", "test2@t.com")
            val portfolio = `given portfolio with id`(PortfolioId("1"))
            val publishedArticle = Article(ArticleId("2"), ArticleTitle("title"), ArticleContent("text"))

            app.publishArticle(portfolio, publishedArticle)

            with(emailSenderPolicy) {
                hasBeenSent("test1@t,com", ArticlePublishedEvent(publishedArticle))
                hasBeenSent("test2@t,com", ArticlePublishedEvent(publishedArticle))
            }
        }
    }
})

fun `given portfolio with id`(portfolioId: PortfolioId): Portfolio {
    return Portfolio(portfolioId, CustomerId("123"), listOf())
}

fun TestApplication(eventBus: EventBus) = Application()

interface EmailSenderPolicy : EventListener<ArticlePublishedEvent> {
    fun send(event: ArticlePublishedEvent) = reactTo(event)
}
