import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import main.Account

class AccountTest extends AnyWordSpec with Matchers {
  "An Account" should {
    "accepts a deposit" which {
      "stores a new transaction" in {
        val subject = new Account();
        subject.deposit(100)
        subject.transactions should have length 1
      }
    }
  }
}
