package org.tgranz.kedium.portfolio

import io.kotest.core.spec.style.FeatureSpec
import org.tgranz.kedium.Application
import org.tgranz.kedium.customer.CustomerId
import org.tgranz.kedium.event.EventBus
import org.tgranz.kedium.fakes.FakeEmailSenderPolicy
import org.tgranz.kedium.fakes.InMemoryPortfolioRepository
import org.tgranz.kedium.fixtures.aEmptyPortfolio

class PublishArticlesAcceptanceTest : FeatureSpec({
    val emailSenderPolicy = FakeEmailSenderPolicy()
    val eventBus = EventBus().apply { subscribe(emailSenderPolicy) }
    val repository = InMemoryPortfolioRepository(eventBus)
    val app = TestApplication(repository)

    feature("publish Article in Portfolio") {
        xscenario("quota exceeded") {
        }

        scenario("article published should send email to interested receivers") {
            emailSenderPolicy.addEmailReceivers("test1@t.com", "test2@t.com")
            val customer = repository.`given portfolio with customerId`(CustomerId("123"))
            val publishedArticle = Article(ArticleId("2"), ArticleTitle("title"), ArticleContent("text"))

            app.publishArticle(customer, publishedArticle)

            with(emailSenderPolicy) {
                hasBeenSent("test1@t.com", ArticlePublishedEvent(publishedArticle))
                hasBeenSent("test2@t.com", ArticlePublishedEvent(publishedArticle))
            }
        }
    }
})

private fun PortfolioRepository.`given portfolio with customerId`(customerId: CustomerId): CustomerId {
    save(aEmptyPortfolio(customerId))
    return customerId
}

private fun TestApplication(repository: PortfolioRepository) = Application(ArticleService(repository))
