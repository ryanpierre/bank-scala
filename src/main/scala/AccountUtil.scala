package main

import scala.collection.mutable.ArrayBuffer

trait AccountUtilBase {
  def balance(collection: ArrayBuffer[TransactionBase]): Double
}

object AccountUtil extends AccountUtilBase {
  def balance(collection: ArrayBuffer[TransactionBase]) = {
    collection.map(_.amount).foldLeft(0.0)(_ + _)
  }
}
