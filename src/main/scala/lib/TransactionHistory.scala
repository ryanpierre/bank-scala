package main.lib

import scala.collection.mutable.ArrayBuffer
import java.time.{Instant, Clock}
import main.model.{AccountBase, TransactionBase, TransactionType}

trait TransactionHistoryItemBase {
  def date: Instant
  def amount: Double
  def balance: Double
}

class TransactionHistoryItem(
    val date: Instant,
    val amount: Double,
    val balance: Double
) extends TransactionHistoryItemBase

trait TransactionHistoryBase {
  def getAccountHistory(
      account: AccountBase
  ): ArrayBuffer[TransactionHistoryItemBase]
  def getAccountBalance(account: AccountBase): Double
}

object TransactionHistory extends TransactionHistoryBase {
  private def toHistoryItems(
      transactions: ArrayBuffer[TransactionHistoryItemBase],
      item: TransactionBase
  ): ArrayBuffer[TransactionHistoryItemBase] = {
    return transactions += new TransactionHistoryItem(
      item.date,
      item.amount,
      transactions.foldLeft(item.amount)(_ + _.amount)
    )
  }

  private def sortHistoryByDate(
      t1: TransactionBase,
      t2: TransactionBase
  ): Boolean = {
    return t1.date.isBefore(t2.date)
  }

  def getAccountHistory(
      account: AccountBase
  ): ArrayBuffer[TransactionHistoryItemBase] = {
    account.transactions
      .sortWith(sortHistoryByDate)
      .foldLeft(new ArrayBuffer[TransactionHistoryItemBase]())(
        toHistoryItems
      )
  }

  def getAccountBalance(
      account: AccountBase
  ): Double = {
    account.transactions
      .sortWith(sortHistoryByDate)
      .foldLeft(0.0)(
        _ + _.amount
      )
  }
}
