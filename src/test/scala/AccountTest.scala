import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalamock.scalatest.MockFactory
import main.{Account, BaseTransaction, BaseTransactionFactory}
import scala.collection.mutable.ArrayBuffer

class AccountTest extends AnyWordSpec with Matchers with MockFactory {
  "An Account" should {
    "accepts a deposit" which {
      "stores a new transaction" in {
        val mockTransactionFactory = mock[BaseTransactionFactory]
        val mockDeposit = mock[BaseTransaction]

        (mockTransactionFactory.create _)
          .expects(100)
          .returning(mockDeposit)

        val subject = new Account(new ArrayBuffer(), mockTransactionFactory);

        subject.deposit(100)
        subject.transactions should have length 1
        subject.transactions(0) shouldEqual mockDeposit
      }
    }
    "accept a withdrawal" which {
      "stores a new transaction" in {
        val mockTransactionFactory = mock[BaseTransactionFactory]
        val mockDeposit = mock[BaseTransaction]
        val mockWithdrawal = mock[BaseTransaction]

        (mockTransactionFactory.create _)
          .expects(-50)
          .returning(mockWithdrawal)

        val subject =
          new Account(
            new ArrayBuffer(1).append(mockDeposit),
            mockTransactionFactory
          ) {
            override def balance(): Double = 100.0
          };

        subject.withdraw(50)
        subject.transactions should have length 2
        subject.transactions(1) shouldEqual mockWithdrawal
      }
      "throws an error if the requested amount is more than balance" in {
        val mockTransactionFactory = mock[BaseTransactionFactory]
        val subject = new Account(new ArrayBuffer(), mockTransactionFactory)

        a[RuntimeException] should be thrownBy subject.withdraw(50)
      }
    }
    "calculate balance" which {
      "sums the net amount of all transactions" in {
        val mockTransaction1 = mock[BaseTransaction]
        val mockTransaction2 = mock[BaseTransaction]
        val mockTransaction3 = mock[BaseTransaction]

        (() => mockTransaction1.amount).expects().returning(100.5)
        (() => mockTransaction2.amount).expects().returning(-50.5)
        (() => mockTransaction3.amount).expects().returning(200)

        val subject =
          new Account(
            new ArrayBuffer(3).appendAll(
              Iterable(mockTransaction1, mockTransaction2, mockTransaction3)
            )
          )

        subject.balance() shouldEqual 250
      }
    }
  }
}
