import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalamock.scalatest.MockFactory
import scala.collection.mutable.ArrayBuffer
import main.{AccountUtil, TransactionBase}

class AccountUtilTest extends AnyWordSpec with Matchers with MockFactory {
  "An AccountUtil" should {
    "calculate balance" which {
      "sums the net amount of all transactions in a list" in {
        val mockTransaction1 = mock[TransactionBase]
        val mockTransaction2 = mock[TransactionBase]
        val mockTransaction3 = mock[TransactionBase]

        (() => mockTransaction1.amount).stubs().returning(100.5)
        (() => mockTransaction2.amount).stubs().returning(-50.5)
        (() => mockTransaction3.amount).stubs().returning(200)

        AccountUtil.balance(
          new ArrayBuffer(3).appendAll(
            Iterable(mockTransaction1, mockTransaction2, mockTransaction3)
          )
        ) shouldEqual 250
      }
    }
  }
}
