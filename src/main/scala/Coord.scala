package com.ataraxer.apps.chess.scala

import scala.language.implicitConversions


/*
 * Coord
 *
 * Coord class represents a two-dimensional coordinate on a chess board
 * where first values determines row and second one — column.
 */
object Shift {
  implicit def tupleToShift(tuple: (Int, Int)) = Shift(tuple._1, tuple._2)
}

case class Shift(x: Int, y: Int) {
  def * (m: Int) = Shift(x * m, y * m)
  def * (r: Int, c: Int) = Shift(x * r, y * c)
  def * (m: (Int, Int)) = Shift(x * m._1, y * m._2)
  def + (m: Int) = Shift(x + m, y + m)
  def + (s: Shift) = Shift(x + s.x, y + s.y)
}

case class Coord(row: Int, col: Int) {
  // constant board size value for both dimensions
  val boardSize = Board.sideSize

  // boundaries check
  if (row < 0 || row >= boardSize || col < 0 || col >= boardSize)
    throw new IllegalArgumentException

  // shift operation/method for generating new coordinate by shifting
  // existing coordinate's dimensions values by given delta
  def << (sh: Shift): Option[Coord] = shift(sh)
  def << (tor: Int, toc: Int): Option[Coord] = shift(tor, toc)

  def shift(sh: Shift): Option[Coord] = shift(sh.x, sh.y)
  def shift(rowShift: Int, columnShift: Int): Option[Coord] =
    try {
      Some(Coord(row + rowShift, col + columnShift))
    } catch {
      case e: IllegalArgumentException => None
    }
}

