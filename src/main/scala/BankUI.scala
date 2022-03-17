package main

import java.time.Clock
import scala.collection.mutable.ArrayBuffer

/*
  BankUI

  The BankUI class should store accounts and handle user input to
  control the various features of our bank
 */
class BankUI(
    val accounts: ArrayBuffer[AccountBase] = new ArrayBuffer(),
    val transactionFactory: TransactionFactoryBase = TransactionFactory,
    val accountUtils: AccountUtilsBase = AccountUtils,
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

    if (amount > account.get.transactions.map(_.amount).foldLeft(0.0)(_ + _)) {
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

    statementGenerator.generate(accountUtils.getHistory(account.get))
  }
}
