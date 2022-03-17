package main

import java.time.Clock
import scala.collection.mutable.ArrayBuffer

class Account(
    val transactions: ArrayBuffer[TransactionBase] = new ArrayBuffer(),
    val transactionFactory: TransactionFactoryBase = TransactionFactory
) {
  def balance(): Double = {
    transactions.map(_.amount).foldLeft(0.0)(_ + _)
  }

  def deposit(amount: Double): Unit = {
    if (amount <= 0) {
      throw new IllegalArgumentException("Amount must be greater than 0")
    }

    addNewTransaction(amount)
  }

  def withdraw(amount: Double): Unit = {
    if (amount <= 0) {
      throw new IllegalArgumentException("Amount must be greater than 0")
    }

    if (amount > balance()) {
      throw new RuntimeException("Not enough money!")
    }

    addNewTransaction(-1 * amount)
  }

  private def addNewTransaction(amount: Double): Unit = {
    transactions += transactionFactory.create(amount)
  }
}
