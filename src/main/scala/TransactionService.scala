package main

trait BaseTransactionService {
  def createTransaction(amount: Double): Transaction
}

class TransactionService() extends BaseTransactionService {
  def createTransaction(amount: Double): Transaction = {
    return new Transaction(amount)
  }
}