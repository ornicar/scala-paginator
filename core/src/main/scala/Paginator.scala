package com.github.ornicar.paginator

/**
 * Simple, cached implementation of PaginatorLike
 * Based on adapters
 */
case class Paginator[A](
  val adapter: Adapter[A],
  val currentPage: Int = 1,
  val maxPerPage: Int = 10
) extends PaginatorLike[A] {

  assert(maxPerPage > 0, "Max per page must be greater than zero")
  assert(currentPage > 0, "Current page must be greater than zero")

  /**
   * Returns the results for the current page.
   * The result is cached.
   */
  lazy val currentPageResults: Seq[A] =
    adapter.slice((currentPage -1) * maxPerPage, maxPerPage)

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
}
