package paginator

class Paginator[A](
  val adapter: Adapter[A],
  val maxPerPage: Int = 10,
  val currentPage: Int = 1
) extends PaginatorLike[A] {

  assert(maxPerPage > 0, "Max per page must be greater than zero")
  assert(currentPage > 0, "Current page must be greater than zero")

  /**
   * Returns the results for the current page.
   */
  def currentPageResults: Seq[A] =
    adapter.slice((currentPage -1) * maxPerPage, maxPerPage)

  /**
   * Returns the number of results.
   */
  def nbResults: Int = adapter.nbResults

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
