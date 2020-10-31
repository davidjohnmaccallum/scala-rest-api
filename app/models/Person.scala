package models

import anorm.{Macro, RowParser}

case class Person(
                   id: Option[Long],
                   firstName: String,
                   lastName: String,
                   email: String,
                   gender: String,
                   ipAddress: String
                 )

object Person {
  val parser: RowParser[Person] = Macro.namedParser[Person]
}