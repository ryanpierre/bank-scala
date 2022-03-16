package main

import java.time.{Instant, Clock}

class TransactionFactory() {
  def create(amount: Double): TransactionBase = {
    return new Transaction(amount)
  }

  def create(amount: Double, clock: Clock): TransactionBase = {
    return new Transaction(amount, clock)
  }
}
