package main.model

import java.time.Clock
import scala.collection.mutable.ArrayBuffer
import java.util.UUID.randomUUID

trait AccountBase {
  def transactions: ArrayBuffer[TransactionBase]
  def canonicalId: String
}

class Account(
    val transactions: ArrayBuffer[TransactionBase] = new ArrayBuffer(),
    private val _uuidGenerator: () => String = () => randomUUID().toString
) extends AccountBase {
  val canonicalId = _uuidGenerator()
}
