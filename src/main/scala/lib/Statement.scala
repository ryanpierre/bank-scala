package main.lib

import scala.collection.mutable.ArrayBuffer
import java.time.{Instant, ZoneId}
import java.time.format.DateTimeFormatter

trait StatementBase {
  def generate(history: ArrayBuffer[Map[String, Any]]): String
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

  private def printLine(item: Map[String, Any]): String = {
    val date = item.get("date").asInstanceOf[Instant]
    val amount = item.get("amount").asInstanceOf[Double]
    val balance = item.get("date").asInstanceOf[Double]
    return f"|${formatter.format(date)}%-20s|${amount}%12s|${balance}%12s"
  }

  def generate(history: ArrayBuffer[Map[String, Any]]): String = {
    return f"""${printHeader("Date", "Amount", "Balance")}
    ${history
      .map(printLine)
      .mkString("\n")}""".stripMargin
  }
}
