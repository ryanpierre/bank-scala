import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalamock.scalatest.MockFactory
import main.client.BankUI
import main.model._
import scala.collection.mutable.ArrayBuffer
import main.lib.TransactionFactory

class BankUITest extends AnyWordSpec with Matchers with MockFactory {
  "A BankUI" should {
    "accept a deposit" which {
      "adds a transaction to an account's history" in {
        val mockAccount = mock[Account]
        val mockTransactionFactory = mock[TransactionFactory]
        val mockTransaction1 = mock[Transaction]
        val mockTransactions = new ArrayBuffer[Transaction]()
        val mockAccounts = ArrayBuffer(mockAccount)
        val subject = new BankUI(mockAccounts)

        (() => mockAccount.id)
          .stubs()
          .returning(0)

        (() => mockAccount.transactions)
          .stubs()
          .returning(mockTransactions)

        (mockTransactionFactory.create _)
          .expects(100)
          .returning(mockTransaction1)

        subject.deposit(0, 100, mockTransactionFactory)

        mockAccount.transactions should contain only mockTransaction1
      }
    }
    "accepts a withdrawal" which {
      "adds a transaction to an account's history" in {}
    }
    "prints a receipt" which {
      "prints the history of each transaction on an account with the balance" in {}
    }
  }
}
