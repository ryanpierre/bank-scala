package main.model

import java.time.Instant
import scala.collection.mutable.ArrayBuffer

trait StatementFormatter {
  val print: (Account) => String
}
