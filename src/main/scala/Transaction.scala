package main

import java.time.LocalDateTime
import java.time.{Instant, Clock}

trait TransactionBase {
  def amount: Double
  def date: Instant
}

class Transaction(
    val amount: Double,
    private val clock: Clock = Clock.systemUTC()
) extends TransactionBase {
  def date = Instant.now(clock)
}
