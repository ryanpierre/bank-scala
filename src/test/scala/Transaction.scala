import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalamock.scalatest.MockFactory
import main.{Transaction, TransactionFactory}
import java.time.{Clock, Instant, ZoneId}

class TransactionTest extends AnyWordSpec with Matchers with MockFactory {
  "A Transaction" should {
    "store transaction data" which {
      "uses the provided clock if one is provided" in {
        val subject = new Transaction(
          100,
          Clock.fixed(
            Instant.parse("2022-03-13T16:45:00.00Z"),
            ZoneId.of("UTC")
          )
        )

        subject.amount shouldBe 100
        subject.date.toString() shouldBe "2022-03-13T16:45:00Z"
      }

    }
  }
}
