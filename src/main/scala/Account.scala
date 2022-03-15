package main

import java.time.Clock

class Account(
    var transactions: Seq[BaseTransaction] = Seq(),
    val transactionService: BaseTransactionService = new TransactionService()
) {

  def balance(): Double = {
    transactions.map(_.amount).reduceLeft(_ + _)
  }

  def deposit(amount: Double): Unit = {
    transactions = transactions :+ transactionService.createTransaction(amount)
  }
}
