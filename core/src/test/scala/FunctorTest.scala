package com.github.ornicar.paginator
package test

import org.scalatest._
import com.github.ornicar.paginator._

class FunctorTest extends FunSuite {

  def paginator[A](seq: Seq[A]) = new Paginator(InMemoryAdapter(seq), 1, 10)

  test("Map empty seq") {
    val p = paginator(Seq[Int]())
    val p2 = p map (_+1)
    assert(p2.currentPageResults === Seq())
  }
  test("Map non empty seq with identity") {
    val p = paginator(Seq(1, 2, 3))
    val p2 = p map identity
    assert(p2.currentPageResults === Seq(1, 2, 3))
  }
  test("Map non empty seq with function") {
    val p = paginator(Seq(1, 2, 3))
    val p2 = p map (_+1)
    assert(p2.currentPageResults === Seq(2, 3, 4))
  }
  test("For comprehension") {
    val p = paginator(Seq(1, 2, 3))
    val p2 = for (r <- p) yield r + 1
    assert(p2.currentPageResults === Seq(2, 3, 4))
  }
  test("Verify the function is lazily applied") {
    val p = paginator(Seq(1, 2, 3))
    val p2 = p map { _ => sys.error("Not yet!") }
    assert(true)
  }
}
