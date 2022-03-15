package main

import java.time.Clock

object Util {
  def mapAndFold[A, B](
      collection: Seq[A],
      initialValue: B,
      mapFn: (A) => B,
      foldFn: (B, B) => B
  ) = {
    collection.map(mapFn).foldLeft(initialValue)(foldFn)
  }
}

class Account(
    var transactions: Seq[BaseTransaction] = Seq(),
    val transactionService: BaseTransactionService = new TransactionService()
) {

  def balance(): Double = {
    Util.mapAndFold[BaseTransaction, Double](
      transactions,
      0.0,
      _.amount,
      _ + _
    )
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
    transactions = transactions :+ transactionService.createTransaction(amount)
  }
}
