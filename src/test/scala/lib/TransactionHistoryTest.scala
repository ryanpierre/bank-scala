import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import main.lib.{TransactionHistory}
import main.model.{AccountBase}
import scala.collection.mutable.ArrayBuffer
import org.scalamock.scalatest.MockFactory
import scala.collection.immutable.HashMap
import java.time.Instant

class TransactionHistoryTest
    extends AnyWordSpec
    with Matchers
    with MockFactory {
  "An TransactionHistory" should {
    "generate an account history from an account" which {
      "creates a new collection of printer compatible history items" in {
        val mockDeposit1 = mock[HashMap[String, Any]]
        val mockDeposit2 = mock[HashMap[String, Any]]
        val mockWithdrawal = mock[HashMap[String, Any]]
        var mockTransactions =
          ArrayBuffer(mockDeposit1, mockDeposit2, mockWithdrawal)
        val mockAccount = mock[AccountBase]
        val mockInstant1 = Instant.parse("2022-03-14T16:45:00.00Z")
        val mockInstant2 = Instant.parse("2022-03-15T17:00:00.00Z")
        val mockInstant3 = Instant.parse("2022-03-16T16:45:00.00Z")

        (mockDeposit1.get _)
          .stubs("amount")
          .returning(100)
        (mockDeposit2.get _)
          .stubs("amount")
          .returning(150)
        (mockWithdrawal.get _)
          .stubs("amount")
          .returning(-50)
        (mockDeposit1.get _)
          .stubs("date")
          .returning(mockInstant1)
        (mockDeposit2.get _)
          .stubs("date")
          .returning(mockInstant2)
        (mockWithdrawal.get _)
          .stubs("date")
          .returning(mockInstant3)

        (() => mockAccount.transactions)
          .stubs()
          .returning(mockTransactions)

        val subject = TransactionHistory.getAccountHistory(
          mockAccount
        )

        subject(0).get("amount") shouldEqual 100
        subject(0).get("date") shouldEqual mockInstant1
        subject(0).get("balance") shouldEqual 100.0

        subject(1).get("amount") shouldEqual 150
        subject(1).get("date") shouldEqual mockInstant2
        subject(1).get("balance") shouldEqual 250.0

        subject(2).get("amount") shouldEqual -50
        subject(2).get("date") shouldEqual mockInstant3
        subject(2).get("balance") shouldEqual 200.0
      }
    }
  }
}
