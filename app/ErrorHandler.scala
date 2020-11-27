import play.api.http.HttpErrorHandler
import play.api.mvc.Results._
import play.api.mvc._
import play.api.libs.json._
import utils.Utils._

import scala.concurrent.Future

/**
 * Return JSON format errors by default.
 */
class ErrorHandler extends HttpErrorHandler {
  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    statsd.incrementCounter(statusCode+"")
    val defaultedMessage = if (!message.isEmpty) message else "Invalid request"
    Future.successful(
      Status(statusCode)(Json.obj("err" -> defaultedMessage))
    )
  }

  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    statsd.incrementCounter("500")
    logger.error("Server error", exception)
    Future.successful(
      InternalServerError(Json.obj("err" -> "Internal server error"))
    )
  }
}
