package controllers

import javax.inject._
import play.api.mvc._
import utils.Utils._

/**
 * For more information https://www.playframework.com/documentation/2.8.x/ScalaActions
 */
@Singleton
class SimpleController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  /**
   * Most simplest controller action.
   */
  def echo: Action[AnyContent] = Action {
    statsd.incrementCounter("simple.echo")
    Ok("Hello")
    /*
    is syntactic sugar for:
    Result(
      header = ResponseHeader(200, Map.empty),
      body = HttpEntity.Strict(ByteString("Hello"), Some("text/plain"))
    )
    */
  }

  /**
   * Second most simplest controller action.
   */
  def greet(name: String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    statsd.incrementCounter("simple.greet")
    Ok(s"Hello $name")
  }

  /**
   * The result is a standard ‘Not implemented yet’ result page.
   */
  def todo: Action[AnyContent] = TODO

  /**
   * Hmm, you can just wack HTML or XML straight in there.
   */
  def someHtml: Action[AnyContent] = Action {
    statsd.incrementCounter("simple.someHtml")
    Ok(<h1>Hello</h1>)
  }

}
