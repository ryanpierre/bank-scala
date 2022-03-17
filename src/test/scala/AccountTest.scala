import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalamock.scalatest.MockFactory
import main.{
  Account,
  Transaction,
  TransactionBase,
  TransactionFactoryBase,
  AccountUtilBase,
  TransactionType,
  DEPOSIT,
  WITHDRAWAL
}
import scala.collection.mutable.ArrayBuffer
import java.time.Instant

class AccountTest extends AnyWordSpec with Matchers with MockFactory {
  "An Account" should {
    "accept a deposit" which {
      "stores a new transaction" in {
        val mockTransactionFactory = mock[TransactionFactoryBase]
        val mockAccountUtil = mock[AccountUtilBase]
        val mockDeposit = mock[TransactionBase]

        (mockTransactionFactory.create: (
            Double,
            TransactionType
        ) => TransactionBase)
          .expects(100, DEPOSIT)
          .returning(mockDeposit)

        val subject = new Account(
          new ArrayBuffer(),
          mockAccountUtil,
          mockTransactionFactory
        );

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
        val mockAccountUtil = mock[AccountUtilBase]
        val mockTransactionFactory = mock[TransactionFactoryBase]
        val mockWithdrawal = mock[TransactionBase]
        val mockTransactions = new ArrayBuffer[TransactionBase]()

        (mockAccountUtil.balance _)
          .expects(mockTransactions)
          .returning(100)

        (mockTransactionFactory.create: (
            Double,
            TransactionType
        ) => TransactionBase)
          .expects(50, WITHDRAWAL)
          .returning(mockWithdrawal)

        val subject =
          new Account(
            mockTransactions,
            mockAccountUtil,
            mockTransactionFactory
          )

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
  }
}
