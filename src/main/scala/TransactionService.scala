package main

trait BaseTransactionService {
  def createTransaction(amount: Double): BaseTransaction
}

class TransactionService() extends BaseTransactionService {
  def createTransaction(amount: Double): BaseTransaction = {
    return new Transaction(amount)
  }
}