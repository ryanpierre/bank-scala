package main

import scala.collection.mutable.ArrayBuffer
import java.time.{Instant, ZoneId}
import java.time.format.DateTimeFormatter

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

trait StatementBase {
  def generate(history: ArrayBuffer[TransactionHistoryItemBase]): String
}

object Statement extends StatementBase {
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

  private def printLine(item: TransactionHistoryItemBase): String = {
    return f"|${formatter.format(item.date)}%-20s|${item.amount}%12s|${item.balance}%12s"
  }

  def generate(history: ArrayBuffer[TransactionHistoryItemBase]): String = {
    return f"""${printHeader("Date", "Amount", "Balance")}
    ${history
      .map(printLine)
      .mkString("\n")}""".stripMargin
  }
}
