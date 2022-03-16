import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalamock.scalatest.MockFactory
import main.{Account, Transaction, TransactionBase, TransactionFactory}
import scala.collection.mutable.ArrayBuffer
import java.time.Instant

class AccountTest extends AnyWordSpec with Matchers with MockFactory {
  "An Account" should {
    "accept a deposit" which {
      "stores a new transaction" in {
        val mockTransactionFactory = mock[TransactionFactory]
        val mockDeposit = mock[Transaction]

        (mockTransactionFactory.create: (Double) => Transaction)
          .expects(100)
          .returning(mockDeposit)

        val subject = new Account(new ArrayBuffer(), mockTransactionFactory);

        subject.deposit(100)
        subject.transactions should have length 1
        subject.transactions(0) shouldEqual mockDeposit
      }
      "throws an error if the amount is less than or equal to 0" in {
        val subject = new Account()

        an[IllegalArgumentException] should be thrownBy subject
          .deposit(-50)
      }
    }
    "accept a withdrawal" which {
      "stores a new transaction" in {
        val mockTransactionFactory = mock[TransactionFactory]
        val mockWithdrawal = mock[Transaction]

        (mockTransactionFactory.create: (Double) => Transaction)
          .expects(-50)
          .returning(mockWithdrawal)

        val subject =
          new Account(new ArrayBuffer(), mockTransactionFactory) {
            override def balance(): Double = 100.0
          };

        subject.withdraw(50)
        subject.transactions should have length 1
        subject.transactions(0) shouldEqual mockWithdrawal
      }
      "throws an error if the requested amount is more than balance" in {
        val subject = new Account()

        a[RuntimeException] should be thrownBy subject.withdraw(50)
      }
      "throws an error if the amount is less than or equal to 0" in {
        val subject = new Account()

        an[IllegalArgumentException] should be thrownBy subject
          .withdraw(-50)
      }
    }
    "calculate balance" which {
      "sums the net amount of all transactions" in {
        val mockTransaction1 = mock[TransactionBase]
        val mockTransaction2 = mock[TransactionBase]
        val mockTransaction3 = mock[TransactionBase]

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
