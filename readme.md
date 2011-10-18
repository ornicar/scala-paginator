# Generic paginator for scala

- Provides pagination for any data provider: DB, remote API, memory, ...
- Based on a simple adapter pattern.
- Fully functional.

## Example

```scala
import com.github.ornicar.paginator._

val adapter   = InMemoryAdapter('a' to 'z')
val paginator = Paginator(adapter, currentPage = 2, maxPerPage = 5)

paginator.nbResults           // 26
paginator.nbPages             // 6
paginator.currentPageResults  // Vector('f', 'g', 'h', 'i', 'j')
paginator.currentPage         // 2
paginator.maxPerPage          // 5
paginator.hasToPaginate       // True
paginator.hasNextPage         // True
paginator.hasPreviousPage     // True
paginator.nextPage            // Some(3)
paginator.previousPage        // Some(1)
```

## Adapters

### InMemoryAdapter

This basic implementation of the adapter paginates a `Seq`. Example:

```scala
val adapter = InMemoryAdapter('a' to 'z')

val paginator = Paginator(adapter)
```

### MongoDB Salat adapter

Allows to paginate on any MongoDB query:

```scala
import com.github.ornicar.paginator.SalatAdapter                // I assume you also import the salat stuff

case class Person(name: String)                                 // Say we have a basic model class

object PersonDAO extends SalatDAO[Person, ObjectId](collection) // And a DAO for the model

val query     = MongoDBObject("name" -> "Joe")                    // This is a normal salat query

val adapter   = SalatAdapter(PersonDAO, query)                    // And a shiny mongodb paginator adapter

val paginator = Paginator(adapter)
```

The adapter will take care of adding the `skip` and `limit` options
to the mongodb query.
To use this adapter, you must include the `salat-adapter` project.

## Create another adapter

To create a new adapter, just implement the 2 methods of this trait:

```scala
trait Adapter[A] {

  /**
   * Returns the number of results.
   */
  def nbResults: Int

  /**
   * Returns an slice of the results.
   *
   * @param   offset    The number of elements to skip, starting from zero
   * @param   length    The maximum number of elements to return
   */
  def slice(offset: Int, length: Int): Seq[A]
}
```

Use the provided `InMemoryAdapter` and `SalatAdapter` as implementation examples.
