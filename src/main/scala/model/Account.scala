package main.model

import java.time.{Instant, Clock}
import scala.collection.mutable.ArrayBuffer
import java.util.UUID.randomUUID

trait AccountBase {
  def transactions: ArrayBuffer[Map[String, Any]]
  def canonicalId: String
}
class Account(
    val transactions: ArrayBuffer[Map[String, Any]] = new ArrayBuffer(),
    private val _uuidGenerator: () => String = () => randomUUID().toString
) extends AccountBase {
  val canonicalId = _uuidGenerator()
}
