package com.github.ornicar.paginator

trait Adapter[A] {

  /**
   * Returns the total number of results.
   */
  def nbResults: Int

  /**
   * Returns a slice of the results.
   *
   * @param   offset    The number of elements to skip, starting from zero
   * @param   length    The maximum number of elements to return
   */
  def slice(offset: Int, length: Int): Seq[A]

  /**
   * FUNCTOR INTERFACE
   */

  def map[B](f: A => B): Adapter[B] = {
    new Adapter[B] {
      def nbResults = Adapter.this.nbResults
      def slice(offset: Int, length: Int) = Adapter.this.slice(offset, length) map f
    }
  }
}
