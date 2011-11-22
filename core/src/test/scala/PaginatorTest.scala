package com.github.ornicar.paginator.test.adapter

import org.scalatest._
import com.github.ornicar.paginator._
import com.github.ornicar.paginator.InMemoryAdapter

class PaginatorTest extends FunSuite {

  test("Empty seq") {
    val a = makeAdapter[Int]()
    Paginator(a, 1, 10).fold( { f => fail(f) }, { p =>
      assert(p.currentPage === 1)
      assert(p.maxPerPage === 10)
      assert(p.nbResults === 0)
      assert(p.currentPageResults === Seq())
      assert(p.nbPages === 0)
    })
  }

  test("Big seq") {
    val a = makeAdapter('a' to 'z')
    Paginator(a, 1, 10).fold( { f => fail(f) }, { p =>
      assert(p.currentPageResults === ('a' to 'j'))
      assert(p.nbResults === 26)
      assert(p.nbPages === 3)
    })
  }

  test("Previous page") {
    val a = makeAdapter('a' to 'z')
    Paginator(a, 1, 10).fold( { f => fail(f) }, { p =>
      assert(p.previousPage === None)
      assert(p.hasPreviousPage === false)
    })
    Paginator(a, 2, 10).fold( { f => fail(f) }, { p =>
      assert(p.previousPage === Option(1))
      assert(p.hasPreviousPage === true)
    })
  }

  test("Next page") {
    val a = makeAdapter('a' to 'z')
    Paginator(a, 3, 10).fold( { f => fail(f) }, { p =>
      assert(p.nextPage === None)
      assert(p.hasNextPage === false)
    })
    Paginator(a, 2, 10).fold( { f => fail(f) }, { p =>
      assert(p.nextPage === Option(3))
      assert(p.hasNextPage === true)
    })
  }

  private def makeAdapter[A](s: Seq[A] = Seq()) =
    new InMemoryAdapter[A](s)
}
