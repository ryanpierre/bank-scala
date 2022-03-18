import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalamock.scalatest.MockFactory
import main.client.BankUI
import main.lib.{TransactionHistoryBase, StatementBase}
import main.model.{AccountBase}
import scala.collection.mutable.ArrayBuffer
import scala.collection.immutable.HashMap
import java.time.Instant

class BankUITest extends AnyWordSpec with Matchers with MockFactory {
  "A BankUI" should {
    "accept a deposit" which {
      "stores a new transaction on the specified account" in {
        val mockAccount = mock[AccountBase]
        val mockDeposit = mock[HashMap[String, Any]]
        var mockTransactions = new ArrayBuffer[Map[String, Any]]()

        (() => mockAccount.canonicalId)
          .stubs()
          .returning("12345-6789")

        (() => mockAccount.transactions)
          .stubs()
          .returning(mockTransactions)

        val subject = new BankUI(
          new ArrayBuffer(1).addOne(mockAccount)
        ).deposit("12345-6789", 100)

        mockAccount.transactions should have length 1
        mockAccount.transactions(0) shouldEqual mockDeposit
      }

      "throws an error if the amount is less than or equal to 0" in {
        val mockAccount = mock[AccountBase]

        (() => mockAccount.canonicalId)
          .stubs()
          .returning("12345-6789")

        val subject = new BankUI(new ArrayBuffer(1).addOne(mockAccount))

        val thrown = the[IllegalArgumentException] thrownBy subject
          .deposit("12345-6789", -50)

        thrown.getMessage should equal("Must enter an amount greater than 0")
      }
      "throws an error if the account can't be found" in {
        val mockAccount = mock[AccountBase]

        (() => mockAccount.canonicalId)
          .stubs()
          .returning("98765-4321")

        val subject = new BankUI(new ArrayBuffer(1).addOne(mockAccount))

        val thrown = the[IllegalArgumentException] thrownBy subject
          .deposit("12345-6789", 50)

        thrown.getMessage should equal("Invalid account id")
      }
    }
    "accept a withdrawal" which {
      "stores a new transaction on the specified account" in {
        val mockAccount = mock[AccountBase]
        val mockDeposit = mock[HashMap[String, Any]]
        val mockWithdrawal = mock[HashMap[String, Any]]
        var mockTransactions = ArrayBuffer(mockDeposit)

        (mockDeposit.get _)
          .expects("amount")
          .returning(100)

        (() => mockAccount.canonicalId)
          .stubs()
          .returning("12345-6789")

        (() => mockAccount.transactions)
          .stubs()
          .returning(mockTransactions)

        val subject = new BankUI(
          new ArrayBuffer(1).addOne(mockAccount)
        ).withdraw("12345-6789", 50)

        mockAccount.transactions should have length 2
        mockAccount.transactions(1) shouldEqual mockWithdrawal
      }

      "throws an error if the amount is less than or equal to 0" in {
        val mockAccount = mock[AccountBase]

        (() => mockAccount.canonicalId)
          .stubs()
          .returning("12345-6789")

        val subject = new BankUI(new ArrayBuffer().addOne(mockAccount))

        val thrown = the[IllegalArgumentException] thrownBy subject
          .withdraw("12345-6789", -50)

        thrown.getMessage should equal("Must enter an amount greater than 0")
      }
      "throws an error if the account can't be found" in {
        val mockAccount = mock[AccountBase]

        (() => mockAccount.canonicalId)
          .expects()
          .returning("98765-4321")

        val subject = new BankUI(new ArrayBuffer(1).addOne(mockAccount))

        val thrown = the[IllegalArgumentException] thrownBy subject
          .withdraw("12345-6789", 50)

        thrown.getMessage should equal("Invalid account id")
      }
      "throws an error if the withdrawal exceeds the balance of the account" in {
        val mockDeposit = mock[HashMap[String, Any]]
        var mockTransactions = ArrayBuffer(mockDeposit)
        val mockAccount = mock[AccountBase]

        (mockDeposit.get _)
          .expects("amount")
          .returning(50)

        (() => mockAccount.canonicalId)
          .expects()
          .returning("12345-6789")

        (() => mockAccount.transactions)
          .expects()
          .returning(mockTransactions)

        val subject = new BankUI(new ArrayBuffer(1).addOne(mockAccount))

        val thrown = the[RuntimeException] thrownBy subject
          .withdraw("12345-6789", 100)

        thrown.getMessage should equal("Not enough money!")
      }
    }
    "print an account statement" which {
      "generates historical balance data" in {
        val mockDeposit = mock[HashMap[String, Any]]
        var mockTransactions = ArrayBuffer(mockDeposit)
        val mockAccount = mock[AccountBase]
        val mockTransactionHistory = mock[TransactionHistoryBase]
        val mockHistory = ArrayBuffer(mockHistoryItem)
        val mockStatement = mock[StatementBase]

        (mockTransactionHistory.getAccountHistory _)
          .expects(mockAccount)
          .returning(mockHistory)

        (mockStatement.generate _)
          .expects(mockHistory)
          .returning("I am a statement")

        (() => mockAccount.canonicalId)
          .expects()
          .returning("12345-6789")

        val subject = new BankUI(
          new ArrayBuffer(1).addOne(mockAccount),
          mockTransactionHistory,
          mockStatement
        )

        subject.printStatement("12345-6789") shouldEqual "I am a statement"
      }
    }
  }
}
