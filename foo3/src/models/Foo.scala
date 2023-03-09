package models

import cats.syntax.all._
import io.circe._

enum Status(val value: Int) {
  case Normal extends Status(0)
  case Delete extends Status(-1)
}

case class Foo(
  id: Foo.Id,
  name: String,
  status: Status
) derives Codec.AsObject

object Foo {

  opaque type Id = Long

  given Codec[Id] = { // using Decoder[Long] or Encoder[Long] leads to Infinite loop or NullPointerException
    Codec.from(Decoder.decodeLong, Encoder.encodeLong)
  }

  given Codec[Status] = {
    Codec.from(Decoder[Int], Encoder[Int]).iemap[Status](i => Status.values.find(_.value == i).toRight(s"Invalid status ${i}"))(_.value)
  }

}
