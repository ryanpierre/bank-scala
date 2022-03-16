package main

import java.time.Clock
import scala.collection.mutable.ArrayBuffer

class Account(
    val transactions: ArrayBuffer[BaseTransaction] = new ArrayBuffer(),
    val transactionFactory: BaseTransactionFactory = new TransactionFactory()
) {

  def balance(): Double = {
    transactions.map(_.amount).foldLeft(0.0)(_ + _)
  }

  def deposit(amount: Double): Unit = {
    addTransaction(amount)
  }

  def withdraw(amount: Double): Unit = {
    if (amount > balance()) {
      throw new RuntimeException("Not enough money!")
    }

    addTransaction(-1 * amount)
  }

  private def addTransaction(amount: Double): Unit = {
    transactions += transactionFactory.create(amount)
  }
}
