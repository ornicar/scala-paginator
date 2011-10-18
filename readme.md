# Generic paginator for scala

Offers pagination for any data provider: DB, remote API, ...

Based on a simple adapter trait:

```scala
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
```
