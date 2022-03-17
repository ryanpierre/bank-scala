import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalamock.scalatest.MockFactory
import main.{TransactionBase, TransactionFactory, DEPOSIT, WITHDRAWAL}
import java.time.{Clock, Instant, ZoneId}

class TransactionFactoryTest
    extends AnyWordSpec
    with Matchers
    with MockFactory {
  "A TransactionFactory" should {
    "create a transaction" which {
      "returns a transaction to the caller" in {
        val transaction = TransactionFactory.create(100, DEPOSIT)

        transaction shouldBe a[TransactionBase]
        transaction.amount shouldBe 100
      }
      "respects withdrawal or deposit before creation" in {
        val transaction = TransactionFactory.create(100, WITHDRAWAL)

        transaction shouldBe a[TransactionBase]
        transaction.amount shouldBe -100
      }
    }
  }
}
