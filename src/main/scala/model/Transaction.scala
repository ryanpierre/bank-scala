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
    private val _amount: Double,
    private val _trType: TransactionType,
    private val _clock: Clock = Clock.systemUTC()
) extends TransactionBase {
  def amount = _trType match {
    case DEPOSIT    => _amount
    case WITHDRAWAL => -1 * _amount
  }
  def date = Instant.now(_clock)
}
