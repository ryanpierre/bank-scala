package main.lib

import scala.collection.mutable.ArrayBuffer
import scala.collection.immutable.HashMap
import java.time.{Instant, Clock}
import main.model.{AccountBase}

trait TransactionHistoryBase {
  def getAccountHistory(
      account: AccountBase
  ): ArrayBuffer[Map[String, Any]]
  def getAccountBalance(account: AccountBase): Double
}

object TransactionHistory extends TransactionHistoryBase {
  private def toHistoryItems(
      transactions: ArrayBuffer[Map[String, Any]],
      item: Map[String, Any]
  ): ArrayBuffer[Map[String, Any]] = {
    if (!item.contains("date") || !item.contains("amount")) {
      throw new IllegalArgumentException("Provided Map is invalid")
    }

    val date: Instant = item.get("date").asInstanceOf[Instant]
    val amount: Double = item.get("amount").asInstanceOf[Double]

    return transactions += HashMap(
      "date" -> date,
      "amount" -> amount,
      "balance" -> transactions.foldLeft(amount)(
        _ + _.get("amount").asInstanceOf[Double]
      )
    )
  }

  private def sortHistoryByDate(
      t1: Map[String, Any],
      t2: Map[String, Any]
  ): Boolean = {
    if (!t1.contains("date") || !t2.contains("date")) {
      throw new IllegalArgumentException("Provided Map is invalid")
    }

    val date1: Instant = t1.get("date").asInstanceOf[Instant]
    val date2: Instant = t2.get("date").asInstanceOf[Instant]

    return date1.isBefore(date2)
  }

  def getAccountHistory(
      account: AccountBase
  ): ArrayBuffer[Map[String, Any]] = {
    account.transactions
      .sortWith(sortHistoryByDate)
      .foldLeft(new ArrayBuffer[Map[String, Any]]())(
        toHistoryItems
      )
  }

  def getAccountBalance(
      account: AccountBase
  ): Double = {
    account.transactions
      .sortWith(sortHistoryByDate)
      .foldLeft(0.0)(
        _ + _.get("amount").asInstanceOf[Double]
      )
  }
}
