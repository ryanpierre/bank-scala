package main

import java.time.{Instant, Clock}

class TransactionFactory() {
  def create(amount: Double): Transaction = {
    return new Transaction(amount)
  }

  def create(amount: Double, clock: Clock): Transaction = {
    return new Transaction(amount, clock)
  }
}
