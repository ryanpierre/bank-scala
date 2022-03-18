package main.client

import java.time.Clock
import scala.collection.mutable.ArrayBuffer
import main.lib.{
  Statement,
  TransactionHistory,
  TransactionHistoryBase,
  StatementBase
}
import main.model.{AccountBase}
import scala.collection.immutable.HashMap
import java.time.Instant

class BankUI(
    val accounts: ArrayBuffer[AccountBase] = new ArrayBuffer(),
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

    account.get.transactions += HashMap(
      "amount" -> amount,
      "date" -> Instant.now()
    )
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

    account.get.transactions += HashMap(
      "amount" -> -1 * amount,
      "date" -> Instant.now()
    )
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
