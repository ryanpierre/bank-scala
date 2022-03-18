import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalamock.scalatest.MockFactory
import main.model.{Transaction, DEPOSIT, WITHDRAWAL}
import java.time.{Clock, Instant, ZoneId}

class TransactionTest extends AnyWordSpec with Matchers with MockFactory {
  "A Transaction" should {
    "store transaction data" which {
      "uses the provided clock if one is provided" in {
        val subject = new Transaction(
          100,
          DEPOSIT,
          Clock.fixed(
            Instant.parse("2022-03-13T16:45:00.00Z"),
            ZoneId.of("UTC")
          )
        )

        subject.date.toString() shouldBe "2022-03-13T16:45:00Z"
      }

      "respects the transaction type and sets amount appropriately" in {
        val deposit = new Transaction(
          100,
          DEPOSIT
        )
        val withdrawal = new Transaction(
          100,
          WITHDRAWAL
        )

        deposit.amount shouldBe 100
        withdrawal.amount shouldBe -100
      }
    }
  }
}
