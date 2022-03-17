package main

import java.time.Clock
import scala.collection.mutable.ArrayBuffer
import java.util.UUID.randomUUID

trait AccountBase {
  def transactions: ArrayBuffer[TransactionBase]
  def canonicalId: String
}
/*
  Account

  The Account class should handle the storage of transactions and contain an identifier
 */
class Account(
    val transactions: ArrayBuffer[TransactionBase] = new ArrayBuffer(),
    private val uuidGenerator: () => String = () => randomUUID().toString
) extends AccountBase {
  val canonicalId = uuidGenerator()
}
