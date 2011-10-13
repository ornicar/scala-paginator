package com.github.ornicar.paginator

trait Adapter[A] {

  /**
   * Returns the total number of results.
   */
  def nbResults: Int

  /**
   * Returns a slice of the results.

   * @param   offset    The number of elements to skip, starting at zero
   * @param   length    The maximum number of elements to return
   */
  def slice(offset: Int, length: Int): Seq[A]
}
