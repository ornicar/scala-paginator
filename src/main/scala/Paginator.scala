package paginator

trait PaginatorInterface[A] {

  /**
   * Returns the max per page.
   */
  def maxPerPage: Int

  /**
   * Returns the current page.
   */
  def currentPage: Int

  /**
   * Returns the results for the current page.
   */
  def currentPageResults: Seq[A]

  /**
   * Returns the number of results.
   */
  def nbResults: Int

  /**
   * Returns the number of pages.
   */
  def nbPages: Int

  /**
   * Returns whether we have to paginate or not.
   * This is true if the number of results is higher than the max per page.
   */
  def hasToPaginate: Boolean

  /**
   * Returns whether there is previous page or not.
   */
  def hasPreviousPage: Boolean

  /**
   * Returns the previous page.
   */
  def previousPage: Option[Int]

  /**
   * Returns whether there is next page or not.
   */
  def hasNextPage: Boolean

  /**
   * Returns the next page.
   */
  def nextPage: Option[Int]
}
