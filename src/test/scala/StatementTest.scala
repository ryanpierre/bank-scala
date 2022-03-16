import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalamock.scalatest.MockFactory

class StatementTest extends AnyWordSpec with Matchers with MockFactory {
  "A Statement" should {
    "print" which {
      "which takes an account history returns a string in the account statement format" in {}
    }
  }
}
