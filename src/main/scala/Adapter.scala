package paginator

trait Adapter[A] {

  /**
   * Returns the number of results.
   */
  def nbResults: Int

  /**
   * Returns an slice of the results.
   */
  def slice(offset: Int, length: Int): Seq[A]
}
