package main.model

import java.time.LocalDateTime
import java.time.{Instant, Clock}

sealed trait TransactionType
case object WITHDRAWAL extends TransactionType
case object DEPOSIT extends TransactionType

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
