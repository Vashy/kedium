package org.tgranz.kedium.portfolio

import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.collections.shouldContain
import org.tgranz.kedium.Application
import org.tgranz.kedium.customer.CustomerId
import org.tgranz.kedium.event.Event
import org.tgranz.kedium.event.EventBus
import org.tgranz.kedium.event.EventPolicy
import org.tgranz.kedium.fakes.FakeEmailSenderPolicy
import org.tgranz.kedium.fakes.InMemoryPortfolioRepository
import org.tgranz.kedium.fixtures.aEmptyPortfolio

class ArticlesAcceptanceTest : FeatureSpec({
    val emailSenderPolicy = FakeEmailSenderPolicy()
    val uiReadModelUpdatedPolicy = UIReadModelUpdatedPolicy()
    val eventBus = EventBus().apply { subscribe(emailSenderPolicy) }
    val repository = InMemoryPortfolioRepository(eventBus)
    val app = TestApplication(repository)

    feature("Article Publishing") {
        xscenario("quota exceeded") {
        }

        scenario("customer can publish an article and receive an email") {
            emailSenderPolicy.addEmailReceivers("test1@t.com", "test2@t.com")
            val customer = repository.`given portfolio with customerId`(CustomerId("123"))
            val publishedArticle = Article(ArticleId("2"), ArticleTitle("title"), ArticleContent("text"))

            app.publishArticle(customer, publishedArticle)

            with(emailSenderPolicy) {
                `has been sent`("test1@t.com", ArticlePublishedEvent(publishedArticle))
                `has been sent`("test2@t.com", ArticlePublishedEvent(publishedArticle))
            }
        }
    }

    xfeature("Article Pinning") {
        scenario("customer can pin an article") {
            val customer = repository.`given portfolio with customerId`(CustomerId("123"))
            val pinnedArticle = Article(ArticleId("2"), ArticleTitle("title"), ArticleContent("text"))

            app.pinArticle(customer, pinnedArticle)

            with(uiReadModelUpdatedPolicy) {
                `ui has been updated pinning`(pinnedArticle)
            }
        }
    }
})

private fun PortfolioRepository.`given portfolio with customerId`(customerId: CustomerId): CustomerId {
    save(aEmptyPortfolio(customerId))
    return customerId
}

private fun TestApplication(repository: PortfolioRepository) = Application(ArticleService(repository))

class UIReadModelUpdatedPolicy : EventPolicy {
    private val uiUpdates = mutableListOf<ArticlePinnedEvent>()

    override fun supports(event: Event): Boolean = event is ArticlePinnedEvent

    override fun onEvent(event: Event) {
        uiUpdates += event as ArticlePinnedEvent
    }

    fun `ui has been updated pinning`(article: Article) {
        withClue(
            "uiUpdates should contain $article, but does not.\n" +
                    "uiUpdates: [${uiUpdates.joinToString("; ")}]"
        ) {
            uiUpdates shouldContain article
        }
    }
}
