package com.github.ornicar.paginator

import com.github.ornicar.jdubext.Database
import com.codahale.jdub.{ SingleRowQuery, FlatCollectionQuery, Row }
import collection.generic.CanBuildFrom

abstract class JdubextAdapter[M[A] <: Seq[A], A](implicit val bf: CanBuildFrom[M[A], A, M[A]])
extends Adapter[A] {

  def database: Database
  def countQuery: SingleRowQuery[Int]
  def selectQuery: FlatCollectionQuery[M, A]

  def nbResults: Int = database(countQuery)

  def slice(offset: Int, length: Int): M[A] =
    database(new FlatCollectionQuery[M, A] {
      val sql = selectQuery.sql ++ " LIMIT ? OFFSET ?"
      val values = selectQuery.values ++ Seq(length, offset)
      def flatMap(row: Row) = selectQuery flatMap row
    })
}
