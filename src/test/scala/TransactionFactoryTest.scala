import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalamock.scalatest.MockFactory
import main.{TransactionBase, TransactionFactory}
import java.time.{Clock, Instant, ZoneId}

class TransactionFactoryTest
    extends AnyWordSpec
    with Matchers
    with MockFactory {
  "A TransactionFactory" should {
    "create a transaction" which {
      "returns a transaction to the caller" in {
        val transaction = TransactionFactory.create(100)

        transaction shouldBe a[TransactionBase]
        transaction.amount shouldBe 100
      }
      "uses the provided clock if one is provided" in {
        val transaction = TransactionFactory.create(
          100,
          Clock.fixed(
            Instant.parse("2022-03-13T16:45:00.00Z"),
            ZoneId.of("UTC")
          )
        )

        transaction shouldBe a[TransactionBase]
        transaction.amount shouldBe 100
        transaction.date.toString() shouldBe "2022-03-13T16:45:00Z"
      }

    }
  }
}
