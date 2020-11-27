package utils

import com.timgroup.statsd.NonBlockingStatsDClient
import play.api.Logger

object Utils {
  val logger: Logger = Logger("application")
  val statsd = new NonBlockingStatsDClient("scala-rest-client", "localhost", 8125)
}
