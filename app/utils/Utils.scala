package utils

import com.timgroup.statsd.NonBlockingStatsDClient
import javax.inject._
import play.api.{Configuration, Logger}

@Singleton
class Utils @Inject() (config: Configuration) {
  val logger: Logger = Logger("application")
  val statsd = new NonBlockingStatsDClient(
    config.get[String]("statsd.prefix"),
    config.get[String]("statsd.hostname"),
    config.get[Int]("statsd.port")
  )
}
