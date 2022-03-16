import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalamock.scalatest.MockFactory
import main.{Statement, StatementData, TransactionBase}
import java.time.Instant

class StatementTest extends AnyWordSpec with Matchers with MockFactory {
  "A Statement" should {
    "print" which {
      "which takes an account history returns a string in the account statement format" in {
        val mockTransation1 = mock[StatementData]
        val mockTransation2 = mock[StatementData]
        val mockTransation3 = mock[StatementData]

        (() => mockTransation1.amount).stubs().returning(100)
        (() => mockTransation2.amount).stubs().returning(-50)
        (() => mockTransation3.amount).stubs().returning(150)

        (() => mockTransation1.date)
          .stubs()
          .returning(Instant.parse("2022-03-14T16:45:00.00Z"))
        (() => mockTransation2.date)
          .stubs()
          .returning(Instant.parse("2022-03-16T17:00:00.00Z"))
        (() => mockTransation3.date)
          .stubs()
          .returning(Instant.parse("2022-03-15T16:45:00.00Z"))

        (() => mockTransation1.balance).stubs().returning(100)
        (() => mockTransation2.balance).stubs().returning(250)
        (() => mockTransation3.balance).stubs().returning(200)

        val history = Array(
          mockTransation1,
          mockTransation2,
          mockTransation3
        )

        val subject = new Statement(history)

        subject
          .print() shouldEqual """Date                |Amount      |Balance     
                                 |14/03/2022 16:45    |       100.0|       100.0
                                 |15/03/2022 16:45    |       150.0|       200.0
                                 |16/03/2022 17:00    |       -50.0|       250.0""".stripMargin
      }
    }
  }
}
