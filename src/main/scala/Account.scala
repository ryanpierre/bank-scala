package main

class Account(val transactionService: BaseTransactionService = new TransactionService()) {
  var transactions: Seq[BaseTransaction] = Seq()

  def deposit(amount: Double): Unit = {
    transactions = transactions :+ transactionService.createTransaction(amount)
  }
}
