package main

import java.time.LocalDateTime
import java.time.Instant

trait BaseTransaction {
  def amount: Double
  def date: Instant
}

class Transaction(val amount: Double, val date: Instant) extends BaseTransaction
