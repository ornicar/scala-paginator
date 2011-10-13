package paginator.test.adapter

import org.scalatest._
import paginator._
import paginator.adapter.InMemory

class PaginatorTest extends FunSuite {

  test("Empty seq") {
    val a = makeAdapter[Int]()
    val p = new Paginator(a, 1, 10)
    assert(p.currentPage === 1)
    assert(p.maxPerPage === 10)
    assert(p.nbResults === 0)
    assert(p.currentPageResults === Seq())
    assert(p.nbPages === 0)
  }

  test("Big seq") {
    val a = makeAdapter('a' to 'z')
    val p = new Paginator(a, 1, 10)
    assert(p.currentPageResults === ('a' to 'j'))
    assert(p.nbResults === 26)
    assert(p.nbPages === 3)
  }

  test("Previous page") {
    val a = makeAdapter('a' to 'z')
    val p1 = new Paginator(a, 1, 10)
    assert(p1.previousPage === None)
    assert(p1.hasPreviousPage === false)
    val p2 = new Paginator(a, 2, 10)
    assert(p2.previousPage === Option(1))
    assert(p2.hasPreviousPage === true)
  }

  test("Next page") {
    val a = makeAdapter('a' to 'z')
    val p1 = new Paginator(a, 3, 10)
    assert(p1.nextPage === None)
    assert(p1.hasNextPage === false)
    val p2 = new Paginator(a, 2, 10)
    assert(p2.nextPage === Option(3))
    assert(p2.hasNextPage === true)
  }

  private def makeAdapter[A](s: Seq[A] = Seq()) =
    new InMemory[A](s)
}
