package controllers

import javax.inject._
import play.api.mvc._
import anorm._
import play.api.db.Database
import play.api.libs.json._
import anorm.SqlParser._
import models.Person
import play.api.Logger

@Singleton
class PersonController @Inject()(val controllerComponents: ControllerComponents, db: Database) extends BaseController {

  private implicit val personWrites: OWrites[Person] = Json.writes[Person]
  private implicit val personReads: Reads[Person] = Json.reads[Person]
  val logger = Logger("application")

  def index(id: Long): Action[AnyContent] = Action {
    logger.debug(s"Reading person $id")
    db.withConnection { implicit c =>
      val person =
        SQL"""
             select *
             from people
             where id = $id
             """.as(Person.parser.singleOpt)
      Ok(Json.toJson(person))
    }
  }

  def list(offset: Int, limit: Int): Action[AnyContent] = Action {
    logger.debug(s"Reading people")
    db.withConnection { implicit c =>
      val people =
        SQL"""
             select *
             from people
             order by id desc
             limit $limit offset $offset
             """.as(Person.parser.*)
      Ok(Json.toJson(people))
    }
  }

  def insert(): Action[JsValue] = Action(parse.json) { req =>
    Json.fromJson[Person](req.body) match {
      case JsSuccess(person, _) =>
        db.withConnection { implicit c =>
          val id = SQL"""
                 insert into people (firstname, lastname, email, gender, ipaddress)
                 values (${person.firstName}, ${person.lastName}, ${person.email}, ${person.gender}, ${person.ipAddress})
                 returning id
                 """.as(int("id").single)
          logger.info(s"Inserted person $id")
          Ok(Json.obj("id" -> id))
        }
      case _ => BadRequest(Json.obj("err" -> "Invalid Person"))
    }
  }

  def update(id: Long): Action[JsValue] = Action(parse.json) { req =>
    Json.fromJson[Person](req.body) match {
      case JsSuccess(person, _) =>
        db.withConnection { implicit c =>
          val updateRes = SQL"""
                 update people set
                   firstname = ${person.firstName},
                   lastname = ${person.lastName},
                   email = ${person.email},
                   gender = ${person.gender},
                   ipaddress = ${person.ipAddress}
                 where id = $id
                 """.executeUpdate()
          logger.info(s"Updated person $id")
          Ok(Json.obj("updated" -> updateRes))
        }
      case _ => BadRequest(Json.obj("err" -> "Invalid Person"))
    }
  }

}
