package main

class Account(val transactionService: TransactionService = new TransactionService()) {
  var transactions: Seq[Transaction] = Seq()

  def deposit(amount: Double): Unit = {
    transactions = transactions :+ transactionService.createTransaction(amount)
  }
}
