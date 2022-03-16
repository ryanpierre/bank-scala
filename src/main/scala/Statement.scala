package main

import scala.collection.mutable.ArrayBuffer
import java.time.{Instant, ZoneId}
import java.time.format.DateTimeFormatter

trait StatementData {
  def date: Instant
  def amount: Double
  def balance: Double
}

class TransactionHistoryItem(
    val date: Instant,
    val amount: Double,
    val balance: Double
) extends StatementData

class Statement(val history: Array[StatementData]) {
  private val formatter = DateTimeFormatter
    .ofPattern("dd/MM/yyyy HH:mm")
    .withZone(ZoneId.of("UTC"));

  private def printHeader(
      dateHeader: String,
      amountHeader: String,
      balanceHeader: String
  ): String = {
    return f"$dateHeader%-20s|$amountHeader%-12s|$balanceHeader%-12s"
  }

  private def sortHistoryByDate(
      t1: StatementData,
      t2: StatementData
  ): Boolean = {
    return t1.date.isBefore(t2.date)
  }

  private def printLine(item: StatementData): String = {
    return f"|${formatter.format(item.date)}%-20s|${item.amount}%12s|${item.balance}%12s"
  }

  def print(): String = {
    val sortedHistory = history.sortWith(sortHistoryByDate).map(printLine)

    return f"""${printHeader("Date", "Amount", "Balance")}
    ${sortedHistory.mkString("\n")}""".stripMargin
  }
}
