package main.model

import scala.collection.mutable.ArrayBuffer

trait Account {
  def id: Int
  def transactions: ArrayBuffer[Transaction]
}
