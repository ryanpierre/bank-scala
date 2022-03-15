package main

import java.time.Clock

class Account(
    var transactions: Seq[BaseTransaction] = Seq(),
    val transactionService: BaseTransactionService = new TransactionService()
) {

  def balance(): Double = {
    transactions.map(_.amount).foldLeft(0.0)(_ + _)
  }

  def deposit(amount: Double): Unit = {
    addTransaction(amount)
  }

  def withdraw(amount: Double): Unit = {
    if(amount > balance()) {
      throw new RuntimeException("Not enough money!")
    }

    addTransaction(-1 * amount)
  }

  private def addTransaction(amount: Double): Unit = {
    transactions = transactions :+ transactionService.createTransaction(amount)
  }
}
