package models

import anorm.{Macro, RowParser}

case class Person(
                   id: Long,
                   firstName: String,
                   lastName: String,
                   email: String,
                   gender: String,
                   ipAddress: String
                 )

object Person {
  /**
   * Note the use of indexedParser here. This is because the class
   * fields are in the same sequence as the table fields but thier
   * names are not the same (firstName != first_name).
   */
  val parser: RowParser[Person] = Macro.indexedParser[Person]
}