package com.example.service

import akka.actor.ActorRefFactory
import org.apache.logging.log4j.LogManager
import spray.client.pipelining._
import spray.http.{FormData, HttpRequest, HttpResponse}
import spray.httpx.SprayJsonSupport._
import spray.json.DefaultJsonProtocol

import scala.concurrent.Future
/*
* An example rest client to connect to another rest service
*
* */

case class ExampleResponse(id : String, name : String)

class ExampleClient(config : Configuration, implicit val system: ActorRefFactory) {
  private object JsonProtocol extends DefaultJsonProtocol {
    implicit val exampleResponseFormat = jsonFormat2(ExampleResponse)
  }

  import JsonProtocol._
  import system.dispatcher

  private val log = LogManager.getLogger(this.getClass)

  private val logRequest: HttpRequest => HttpRequest = { r =>
    log.debug(r.toString)
    log.trace(r.entity.data.asString)
    r
  }

  private val logResponse: HttpResponse => HttpResponse = { r =>
    log.debug(r.toString)
    log.trace(r.entity.data.asString)
    r
  }

  private val jsonQuery = addHeader("Accept", "application/json") ~> logRequest ~> sendReceive ~> logResponse

  def requestFuture(id : String) : Future[ExampleResponse] = {
    val pipeline = jsonQuery ~> unmarshal[ExampleResponse]

    pipeline {
      Get(s"${config.ExampleRemoteServer.url}/getUser", FormData(Seq("id" -> id)))
    }
  }
}
