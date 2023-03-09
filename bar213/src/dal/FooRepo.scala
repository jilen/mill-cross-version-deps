package dal

import cats.syntax.all._
import models._
import io.circe.parser
import shapeless._

object FooRepo {

  def getById(id: Long): Option[Foo] = {
    parser.decode[Seq[Foo]](database).toOption.flatMap(_.find(_.id == id))
  }

  private val database = """[{"id": 0, "name":"foo", "status": 0}, {"id": 2, "name":"bar", "status": -1}]"""
}
