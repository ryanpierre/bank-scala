package main

import java.time.Clock
import scala.collection.mutable.ArrayBuffer

class Account(
    val transactions: ArrayBuffer[TransactionBase] = new ArrayBuffer(),
    val accountUtil: AccountUtilBase = AccountUtil,
    val transactionFactory: TransactionFactoryBase = TransactionFactory
) {
  def deposit(amount: Double): Unit = {
    if (amount <= 0) {
      throw new IllegalArgumentException("Must enter an amount greater than 0")
    }

    transactions += transactionFactory.create(amount, DEPOSIT)
  }

  def withdraw(amount: Double): Unit = {
    if (amount <= 0) {
      throw new IllegalArgumentException("Must enter an amount greater than 0")
    }

    if (amount > accountUtil.balance(transactions)) {
      throw new RuntimeException("Not enough money!")
    }

    transactions += transactionFactory.create(amount, WITHDRAWAL)
  }
}
