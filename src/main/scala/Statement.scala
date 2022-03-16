package main

import java.time.Instant

trait StatementData {
  def date: Instant
  def amount: Double
  def balance: Double
}

class Statement(val history: Iterable[StatementData]) {
  def print(): String = {
    return """Printing
             |an aligned
             |multiline
             |string""".stripMargin
  }
}
