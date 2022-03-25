package main.lib

import main.model.Transaction

trait TransactionFactory {
  def create(amount: Double): Transaction
}
