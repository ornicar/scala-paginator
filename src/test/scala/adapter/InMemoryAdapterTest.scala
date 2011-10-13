package paginator.test.adapter

import org.scalatest._
import paginator._
import paginator.adapter._

class InMemoryTest extends FunSuite {

  test("Empty seq") {
    val a = new InMemory(Seq())
    assert(a.nbResults === 0)
    assert(a.slice(0, 10) === Seq())
    assert(a.slice(10, 10) === Seq())
  }

  test("Small seq") {
    val seq = 'a' to 'd'
    val a = new InMemory(seq)
    assert(a.nbResults === 4)
    assert(a.slice(0, 10) === seq)
    assert(a.slice(0, 2) === ('a' to 'b'))
    assert(a.slice(10, 10) === Seq())
  }

  test("Medium seq of boxed chars") {
    case class Box(char: Char)
    def box(seq: Seq[Char]) = seq map { Box(_) }
    val a = new InMemory(box('a' to 'z'))
    assert(a.nbResults === 26)
    assert(a.slice(0, 10) === box('a' to 'j'))
    assert(a.slice(0, 2) === box('a' to 'b'))
    assert(a.slice(2, 2) === box('c' to 'd'))
    assert(a.slice(10, 2) === box('k' to 'l'))
    assert(a.slice(100, 2) === Seq())
  }
}
