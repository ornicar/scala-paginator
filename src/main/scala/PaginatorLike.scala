package paginator

trait PaginatorLike[A] {

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
   * Returns the previous page.
   */
  def previousPage: Option[Int]

  /**
   * Returns the next page.
   */
  def nextPage: Option[Int]

  /**
   * PRE-IMPLEMENTED METHODS
   */

  /**
   * Returns the number of pages.
   */
  def nbPages: Int = scala.math.ceil(nbResults.toFloat / maxPerPage).toInt

  /**
   * Returns whether we have to paginate or not.
   * This is true if the number of results is higher than the max per page.
   */
  def hasToPaginate: Boolean = nbResults > maxPerPage

  /**
   * Returns whether there is previous page or not.
   */
  def hasPreviousPage: Boolean = None == previousPage

  /**
   * Returns whether there is next page or not.
   */
  def hasNextPage: Boolean = None == nextPage
}
