package main

import java.time.{Instant, Clock}

trait BaseTransactionFactory {
  def clock: Clock
  def create(amount: Double): BaseTransaction
}

class TransactionFactory(val clock: Clock = Clock.systemUTC())
    extends BaseTransactionFactory {
  def create(amount: Double): BaseTransaction = {
    if (amount <= 0) {
      throw new IllegalArgumentException("Amount must be greater than 0")
    }

    return new Transaction(amount, Instant.now(clock))
  }
}
