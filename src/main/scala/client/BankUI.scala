package main.client

import java.time.Clock
import scala.collection.mutable.ArrayBuffer
import main.lib.{
  Statement,
  TransactionHistory,
  TransactionHistoryBase,
  TransactionHistoryItemBase,
  StatementBase,
  TransactionFactory,
  TransactionFactoryBase
}
import main.model.{AccountBase, DEPOSIT, WITHDRAWAL}

class BankUI(
    val accounts: ArrayBuffer[AccountBase] = new ArrayBuffer(),
    val transactionFactory: TransactionFactoryBase = TransactionFactory,
    val transactionHistory: TransactionHistoryBase = TransactionHistory,
    val statementGenerator: StatementBase = Statement
) {
  def deposit(canonicalAccountId: String, amount: Double): Unit = {
    val account =
      accounts
        .find(_.canonicalId == canonicalAccountId)

    if (amount <= 0) {
      throw new IllegalArgumentException("Must enter an amount greater than 0")
    }

    if (account.isEmpty) {
      throw new IllegalArgumentException("Invalid account id")
    }

    account.get.transactions += transactionFactory.create(amount, DEPOSIT)
  }

  def withdraw(canonicalAccountId: String, amount: Double): Unit = {
    val account =
      accounts
        .find(_.canonicalId == canonicalAccountId)

    if (amount <= 0) {
      throw new IllegalArgumentException("Must enter an amount greater than 0")
    }

    if (account.isEmpty) {
      throw new IllegalArgumentException("Invalid account id")
    }

    if (amount > transactionHistory.getAccountBalance(account.get)) {
      throw new RuntimeException("Not enough money!")
    }

    account.get.transactions += transactionFactory.create(amount, WITHDRAWAL)
  }

  def printStatement(canonicalAccountId: String): String = {
    val account =
      accounts
        .find(_.canonicalId == canonicalAccountId)

    if (account.isEmpty) {
      throw new IllegalArgumentException("Invalid account id")
    }

    statementGenerator.generate(
      transactionHistory.getAccountHistory(account.get)
    )
  }
}
