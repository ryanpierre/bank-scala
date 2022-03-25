package main.client

import scala.collection.mutable.ArrayBuffer
import main.model._
import main.lib.TransactionFactory

trait IBankUI {
  val accounts: ArrayBuffer[Account]
  def createAccount(): Unit
  def deposit(
      accountId: Int,
      amount: Double,
      transactionFactory: TransactionFactory
  ): Unit
  def withdraw(accountId: Int, amount: Double): Unit
  def print(accountId: Int): String
}

class BankUI(val accounts: ArrayBuffer[Account]) extends IBankUI {
  def createAccount() = {}
  def deposit(
      accountId: Int,
      amount: Double,
      transactionFactory: TransactionFactory
  ) = {
    accounts.find(_.id == accountId) match {
      case None    => throw new RuntimeException("Account did not exist")
      case Some(a) => a.transactions.addOne(transactionFactory.create(amount))
    }
  }
  def withdraw(accountId: Int, amount: Double) = {}
  def print(accountId: Int) = { "" }
}
