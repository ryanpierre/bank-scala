import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import main.model.Account
import scala.collection.mutable.ArrayBuffer

class AccountTest extends AnyWordSpec with Matchers {
  "An Account" should {
    "generate a canonical id" which {
      "calls the uuid generator function on instantiation" in {
        val mockUuidGeneratorFn = () => "12345-6789"
        val subject = new Account(new ArrayBuffer(), mockUuidGeneratorFn)

        subject.canonicalId shouldBe "12345-6789"
      }
    }
  }
}
