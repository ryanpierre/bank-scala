package main 

trait BaseTransaction {
  def amount: Double
}

class Transaction(val amount: Double) extends BaseTransaction