package main

import java.time.{Instant, Clock}

trait TransactionFactoryBase {
  def create(amount: Double): TransactionBase
  def create(amount: Double, clock: Clock): TransactionBase
}

object TransactionFactory extends TransactionFactoryBase {
  def create(amount: Double): TransactionBase = {
    return new Transaction(amount)
  }

  def create(amount: Double, clock: Clock): TransactionBase = {
    return new Transaction(amount, clock)
  }
}
