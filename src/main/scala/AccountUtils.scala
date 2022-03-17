package main

import scala.collection.mutable.ArrayBuffer

trait AccountUtilsBase {
  def getHistory(
      account: AccountBase
  ): ArrayBuffer[TransactionHistoryItemBase]
}

object AccountUtils extends AccountUtilsBase {
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

  def getHistory(
      account: AccountBase
  ): ArrayBuffer[TransactionHistoryItemBase] = {
    account.transactions
      .sortWith(sortHistoryByDate)
      .foldLeft(new ArrayBuffer[TransactionHistoryItemBase]())(
        toHistoryItems
      )
  }
}
