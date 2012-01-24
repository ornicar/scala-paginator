package com.github.ornicar.paginator

/**
 * Simple, cached implementation of PaginatorLike
 * Based on adapters
 */
class Paginator[A] private[paginator] (
    adapter: Adapter[A],
    val currentPage: Int,
    val maxPerPage: Int) extends PaginatorLike[A] {

  /**
   * Returns the results for the current page.
   * The result is cached.
   */
  lazy val currentPageResults: Seq[A] =
    adapter.slice((currentPage - 1) * maxPerPage, maxPerPage)

  /**
   * Returns the number of results.
   * The result is cached.
   */
  lazy val nbResults: Int = adapter.nbResults

  /**
   * Returns the previous page.
   */
  def previousPage: Option[Int] =
    if (currentPage == 1) None else Some(currentPage - 1)

  /**
   * Returns the next page.
   */
  def nextPage: Option[Int] =
    if (currentPage == nbPages) None else Some(currentPage + 1)

  /**
   * FUNCTOR INTERFACE
   */

  def map[B](f: A => B): Paginator[B] = new Paginator(
    adapter = adapter map f,
    currentPage = currentPage,
    maxPerPage = maxPerPage
  )
}

object Paginator {

  def apply[A](
    adapter: Adapter[A],
    currentPage: Int = 1,
    maxPerPage: Int = 10): Either[String, Paginator[A]] =
    if (currentPage <= 0) {
      Left("Max per page must be greater than zero")
    }
    else if (maxPerPage <= 0) {
      Left("Current page must be greater than zero")
    }
    else {
      Right(new Paginator(adapter, currentPage, maxPerPage))
    }

}
