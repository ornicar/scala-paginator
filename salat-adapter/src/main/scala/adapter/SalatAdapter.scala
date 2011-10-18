package com.github.ornicar.paginator
package adapter

import com.novus.salat.dao.DAO
import com.novus.salat._
import com.mongodb.casbah.Imports._

class SalatAdapter[A <: CaseClass, B <: Any](
  dao: DAO[A, B],
  query: DBObject
) extends Adapter[A] {

  def nbResults: Int = dao.count(query).toInt

  def slice(offset: Int, length: Int): Seq[A] =
    dao.find(query).skip(offset).limit(length).toSeq
}
