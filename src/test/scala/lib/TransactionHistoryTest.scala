import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import main.lib.{TransactionHistory, TransactionHistoryItemBase}
import main.model.{AccountBase, TransactionBase}
import scala.collection.mutable.ArrayBuffer
import org.scalamock.scalatest.MockFactory
import java.time.Instant

class TransactionHistoryTest
    extends AnyWordSpec
    with Matchers
    with MockFactory {
  "An TransactionHistory" should {
    "generate an account history from an account" which {
      "creates a new collection of printer compatible history items" in {
        val mockDeposit1 = mock[TransactionBase]
        val mockDeposit2 = mock[TransactionBase]
        val mockWithdrawal = mock[TransactionBase]
        var mockTransactions =
          ArrayBuffer(mockDeposit1, mockDeposit2, mockWithdrawal)
        val mockAccount = mock[AccountBase]
        val mockInstant1 = Instant.parse("2022-03-14T16:45:00.00Z")
        val mockInstant2 = Instant.parse("2022-03-15T17:00:00.00Z")
        val mockInstant3 = Instant.parse("2022-03-16T16:45:00.00Z")

        (() => mockDeposit1.amount)
          .stubs()
          .returning(100)

        (() => mockDeposit2.amount)
          .stubs()
          .returning(150)

        (() => mockWithdrawal.amount)
          .stubs()
          .returning(-50)

        (() => mockDeposit1.date)
          .stubs()
          .returning(mockInstant1)
        (() => mockDeposit2.date)
          .stubs()
          .returning(mockInstant2)
        (() => mockWithdrawal.date)
          .stubs()
          .returning(mockInstant3)

        (() => mockAccount.transactions)
          .stubs()
          .returning(mockTransactions)

        val subject = TransactionHistory.getAccountHistory(
          mockAccount
        )

        subject(0).amount shouldEqual 100
        subject(0).date shouldEqual mockInstant1
        subject(0).balance shouldEqual 100.0

        subject(1).amount shouldEqual 150
        subject(1).date shouldEqual mockInstant2
        subject(1).balance shouldEqual 250.0

        subject(2).amount shouldEqual -50
        subject(2).date shouldEqual mockInstant3
        subject(2).balance shouldEqual 200.0
      }
    }
  }
}
