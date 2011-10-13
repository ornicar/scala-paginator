package paginator
package adapter

class InMemory[A](elems: Seq[A]) extends Adapter[A] {

  /**
   * Returns the number of results.
   */
  def nbResults: elems.length

  /**
   * Returns an slice of the results.
   */
  def slice(offset: Int, length: Int): Seq[A] = elems.slice(offset, length)
}
