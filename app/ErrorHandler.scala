import play.api.http.HttpErrorHandler
import play.api.mvc.Results._
import play.api.mvc._
import play.api.libs.json._

import scala.concurrent.Future
import play.Logger

/**
 * Return JSON format errors by default.
 */
class ErrorHandler extends HttpErrorHandler {
  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    Future.successful(
      Status(statusCode)(Json.obj("err" -> message))
    )
  }

  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    Future.successful {
      println(exception)
      InternalServerError(Json.obj("err" -> "Internal server error"))
    }
  }
}
