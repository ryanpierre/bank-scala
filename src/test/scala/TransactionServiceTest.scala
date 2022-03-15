import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalamock.scalatest.MockFactory
import main.{BaseTransaction, TransactionService}
import java.time.{Clock, Instant, ZoneId}

class TransactionServiceTest
    extends AnyWordSpec
    with Matchers
    with MockFactory {
  "A TransactionService" should {
    "create a transaction" which {
      "returns a transaction to the caller" in {
        val subject = new TransactionService()
        val transaction = subject.createTransaction(100)

        transaction shouldBe a[BaseTransaction]
      }
      "uses the provided clock if one is provided" in {
        val subject = new TransactionService(
          Clock.fixed(
            Instant.parse("2022-03-13T16:45:00.00Z"),
            ZoneId.of("UTC")
          )
        )
        val transaction = subject.createTransaction(100)

        transaction.date.toString() shouldBe "2022-03-13T16:45:00Z"
      }
      "throws an error if the amount is less than or equal to 0" in {
        val subject = new TransactionService()

        an[IllegalArgumentException] should be thrownBy subject
          .createTransaction(0)
      }
    }
  }
}
