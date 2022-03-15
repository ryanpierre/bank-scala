import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalamock.scalatest.MockFactory
import main.{Account, BaseTransaction, BaseTransactionService}

class AccountTest extends AnyWordSpec with Matchers with MockFactory {
  "An Account" should {
    "accepts a deposit" which {
      "stores a new transaction" in {
        // Set up mocks
        val mockTransactionService = mock[BaseTransactionService]
        val mockTransaction = mock[BaseTransaction]
        (mockTransactionService.createTransaction _)
          .expects(100)
          .returning(mockTransaction)

        // Set up test
        val subject = new Account(Seq(), mockTransactionService);
        subject.deposit(100)

        // Assert !
        subject.transactions should have length 1
        subject.transactions(0) should equal(mockTransaction)
      }
    }
    "calculate balance" which {
      "sums the net amount of all transactions" in {
        val mockTransaction1 = mock[BaseTransaction]
        val mockTransaction2 = mock[BaseTransaction]
        val mockTransaction3 = mock[BaseTransaction]

        (mockTransaction1.amount _).expects().returning(100.5)
        (mockTransaction2.amount _).expects().returning(-50.5)
        (mockTransaction3.amount _).expects().returning(200)

        val subject = new Account(Seq(mockTransaction1, mockTransaction2, mockTransaction3))

        subject.balance should equal(250)
      }
    }
  }
}
