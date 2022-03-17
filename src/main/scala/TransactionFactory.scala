package main

import java.time.{Instant, Clock}

sealed trait TransactionType
case object WITHDRAWAL extends TransactionType
case object DEPOSIT extends TransactionType

trait TransactionFactoryBase {
  def create(amount: Double, trType: TransactionType): TransactionBase
}

object TransactionFactory extends TransactionFactoryBase {
  private def getAmount(amount: Double, trType: TransactionType) = {
    if (trType == DEPOSIT) amount else (-1 * amount)
  }
  def create(amount: Double, trType: TransactionType): TransactionBase = {
    return new Transaction(getAmount(amount, trType))
  }
}
