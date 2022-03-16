import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalamock.scalatest.MockFactory
import main.{BaseTransaction, TransactionFactory}
import java.time.{Clock, Instant, ZoneId}

class TransactionFactoryTest
    extends AnyWordSpec
    with Matchers
    with MockFactory {
  "A TransactionFactory" should {
    "create a transaction" which {
      "returns a transaction to the caller" in {
        val subject = new TransactionFactory()
        val transaction = subject.create(100)

        transaction shouldBe a[BaseTransaction]
      }
      "uses the provided clock if one is provided" in {
        val subject = new TransactionFactory(
          Clock.fixed(
            Instant.parse("2022-03-13T16:45:00.00Z"),
            ZoneId.of("UTC")
          )
        )
        val transaction = subject.create(100)

        transaction.date.toString() shouldBe "2022-03-13T16:45:00Z"
      }
      "throws an error if the amount is less than or equal to 0" in {
        val subject = new TransactionFactory()

        an[IllegalArgumentException] should be thrownBy subject
          .create(0)
      }
    }
  }
}
