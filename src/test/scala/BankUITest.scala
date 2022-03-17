import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalamock.scalatest.MockFactory
import main.{
  Account,
  AccountBase,
  Transaction,
  TransactionBase,
  TransactionFactoryBase,
  TransactionType,
  DEPOSIT,
  WITHDRAWAL,
  BankUI
}
import scala.collection.mutable.ArrayBuffer
import java.time.Instant

class BankUITest extends AnyWordSpec with Matchers with MockFactory {
  "A BankUI" should {
    "accept a deposit" which {
      "stores a new transaction on the specified account" in {
        val mockAccount = mock[AccountBase]
        val mockTransactionFactory = mock[TransactionFactoryBase]
        val mockDeposit = mock[TransactionBase]
        var mockTransactions = new ArrayBuffer[TransactionBase]()

        (() => mockAccount.canonicalId)
          .stubs()
          .returning("12345-6789")

        (() => mockAccount.transactions)
          .stubs()
          .returning(mockTransactions)

        (mockTransactionFactory.create: (
            Double,
            TransactionType
        ) => TransactionBase)
          .expects(100, DEPOSIT)
          .returning(mockDeposit)

        val subject = new BankUI(
          new ArrayBuffer(1).addOne(mockAccount),
          mockTransactionFactory
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
        val mockTransactionFactory = mock[TransactionFactoryBase]
        val mockDeposit = mock[TransactionBase]
        val mockWithdrawal = mock[TransactionBase]
        var mockTransactions = ArrayBuffer(mockDeposit)

        (() => mockDeposit.amount)
          .expects()
          .returning(100)

        (() => mockAccount.canonicalId)
          .stubs()
          .returning("12345-6789")

        (() => mockAccount.transactions)
          .stubs()
          .returning(mockTransactions)

        (mockTransactionFactory.create: (
            Double,
            TransactionType
        ) => TransactionBase)
          .expects(50, WITHDRAWAL)
          .returning(mockWithdrawal)

        val subject = new BankUI(
          new ArrayBuffer(1).addOne(mockAccount),
          mockTransactionFactory
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
        val mockDeposit = mock[TransactionBase]
        var mockTransactions = ArrayBuffer(mockDeposit)
        val mockAccount = mock[AccountBase]

        (() => mockDeposit.amount)
          .expects()
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
  }
}
