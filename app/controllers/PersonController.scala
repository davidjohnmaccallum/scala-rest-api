package controllers

import javax.inject._
import play.api.mvc._
import anorm._
import play.api.db.Database
import play.api.libs.json._
import models.Person

@Singleton
class PersonController @Inject()(val controllerComponents: ControllerComponents, db: Database) extends BaseController {

  implicit val personWrites = Json.writes[Person]

  def index(id: Long) = Action { implicit request: Request[AnyContent] =>
    db.withConnection { implicit c =>
      val person = SQL"select * from people where id = ${id}".as(Person.parser.singleOpt)
      Ok(Json.toJson(person)).withHeaders(("Content-Type", "application/json"))
    }
  }

  def list(offset: Int, limit: Int) = Action { implicit request: Request[AnyContent] =>
    db.withConnection { implicit c =>
      val people = SQL"select * from people limit ${limit} offset ${offset}".as(Person.parser.*)
      Ok(Json.toJson(people)).withHeaders(("Content-Type", "application/json"))
    }
  }

}
