package com.example.service

import akka.actor.Actor
import org.joda.time.DateTime
import org.apache.logging.log4j.LogManager
import spray.http.StatusCodes._
import spray.httpx.SprayJsonSupport._
import spray.routing.{HttpService, _}
import spray.util.LoggingContext

import scala.concurrent.Future

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class ExampleServiceActor extends Actor with ExampleService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(route)
}

// this trait defines our service behavior independently from the service actor
trait ExampleService extends HttpService {

  import JsonSerializeProtocol._
  import Util._

  implicit def ec = actorRefFactory.dispatcher

  implicit def myExceptionHandler(implicit log: LoggingContext) =
    ExceptionHandler {
      case e: Exception =>
        requestUri { uri =>
          log.error("Unexpected Exception: " + e.toString, uri)
          complete(InternalServerError, e.toString)
        }
    }

  val log = LogManager.getLogger(classOf[ExampleService])
  val config = new Configuration()
  val exampleClient = new ExampleClient(config, actorRefFactory)
  val da = new DataAccess(config, ec)

  log.info("ExampleService started")

  val route =
    (path("HealthCheck") & get) {
      clientIP { ip =>
        log.debug(s"HealthCheck from $ip")
        //TODO: add more checks here
        complete("Success")
      }
    } ~
      (path("Echo" / Segment) & get) { s =>
        complete {
          log.info(s"Echo $s")
          s
        }
      } ~
      (path("GetData" / Segment) & get) {
        userId: String =>
          complete {
            log.info(s"Get Data for $userId")
            da.findData(userId)
          }
      } ~
      (path("SaveData") & post) {
        formFields('userId, 'info) { (userId: String, info: String) =>
          complete {
            log.info(s"Start Verification $userId $info")

            da.updateData(ExampleDataRow(userId, Some(123), info, DateTime.now().toTimestamp))
          }
        }
      }
}