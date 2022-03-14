package main

class Account() {
  var transactions: Seq[Transaction] = Seq()

  def deposit(amount: Double): Unit = {
    transactions = transactions :+ new Transaction(amount)
  }
}
