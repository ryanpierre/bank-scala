package main.model

import java.time.Instant

trait Transaction {
  def amount: Double
  def date: Instant
}
