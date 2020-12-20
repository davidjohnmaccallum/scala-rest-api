import javax.inject.Inject
import play.api.http.HttpErrorHandler
import play.api.mvc.Results._
import play.api.mvc._
import play.api.libs.json._
import utils.Utils

import scala.concurrent.Future

/**
 * Return JSON format errors by default.
 */
class ErrorHandler @Inject() (utils: Utils) extends HttpErrorHandler {

  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    utils.statsd.incrementCounter(s"$statusCode")
    val defaultedMessage = if (!message.isEmpty) message else "Invalid request"
    Future.successful(
      Status(statusCode)(Json.obj("err" -> defaultedMessage))
    )
  }

  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    utils.statsd.incrementCounter("500")
    utils.logger.error("Server error", exception)
    Future.successful(
      InternalServerError(Json.obj("err" -> "Internal server error"))
    )
  }
}
