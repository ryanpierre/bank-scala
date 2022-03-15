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
        val subject = new Account(mockTransactionService);
        subject.deposit(100)

        // Assert !
        subject.transactions should have length 1
        subject.transactions(0) should equal(mockTransaction)
      }
    }
  }
}
