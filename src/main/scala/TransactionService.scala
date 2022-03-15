package main

import java.time.{Instant, Clock}

trait BaseTransactionService {
  def clock: Clock
  def createTransaction(amount: Double): BaseTransaction
}

class TransactionService(val clock: Clock = Clock.systemUTC())
    extends BaseTransactionService {
  def createTransaction(amount: Double): BaseTransaction = {
    if (amount <= 0) {
      throw new IllegalArgumentException("Amount must be greater than 0")
    }

    return new Transaction(amount, Instant.now(clock))
  }
}
