import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalamock.scalatest.MockFactory
import main.{
  AccountBase,
  Statement,
  TransactionHistoryItemBase,
  TransactionBase
}
import java.time.Instant
import scala.collection.mutable.ArrayBuffer

class StatementTest extends AnyWordSpec with Matchers with MockFactory {
  "A Statement" should {
    "generate a printable statement" which {
      "which takes an account history returns a string in the account statement format" in {
        val mockAccount = mock[AccountBase]
        val mockTransation1 = mock[TransactionHistoryItemBase]
        val mockTransation2 = mock[TransactionHistoryItemBase]
        val mockTransation3 = mock[TransactionHistoryItemBase]

        val mockHistory = ArrayBuffer(
          mockTransation1,
          mockTransation2,
          mockTransation3
        )

        (() => mockTransation1.amount).stubs().returning(100)
        (() => mockTransation2.amount).stubs().returning(150)
        (() => mockTransation3.amount).stubs().returning(-50)

        (() => mockTransation1.date)
          .stubs()
          .returning(Instant.parse("2022-03-14T16:45:00.00Z"))
        (() => mockTransation2.date)
          .stubs()
          .returning(Instant.parse("2022-03-15T17:00:00.00Z"))
        (() => mockTransation3.date)
          .stubs()
          .returning(Instant.parse("2022-03-16T16:45:00.00Z"))

        (() => mockTransation1.balance).stubs().returning(100)
        (() => mockTransation2.balance).stubs().returning(250)
        (() => mockTransation3.balance).stubs().returning(200)

        Statement.generate(
          mockHistory
        ) shouldEqual """Date                |Amount      |Balance     
                        |14/03/2022 16:45    |       100.0|       100.0
                        |15/03/2022 17:00    |       150.0|       250.0
                        |16/03/2022 16:45    |       -50.0|       200.0""".stripMargin
      }
    }
  }
}
