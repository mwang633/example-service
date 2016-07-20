package com.example.service

import org.scalatest.FlatSpec
import org.scalatest.Matchers._
import spray.http.HttpHeaders.`Remote-Address`
import spray.testkit.ScalatestRouteTest
import spray.http._
import StatusCodes._

class ExampleServiceSpec extends FlatSpec with ScalatestRouteTest with ExampleService {
  def actorRefFactory = system

  "GET HealthCheck" should "pass" in {
    Get("/HealthCheck").withHeaders(`Remote-Address`("192.168.3.12")) ~> route ~> check {
      assert(status === OK)
      assert(responseAs[String] == "Success")
    }
  }

  "return echo for GET Echo requests" should "pass" in {
    Get("/Echo/Hello") ~> route ~> check {
      assert(responseAs[String] contains "Hello")
    }
  }

  "return a MethodNotAllowed error for PUT requests to the root path" should "pass" in {
    Put("/Echo/Hello") ~> sealRoute(route) ~> check {
      assert(status === MethodNotAllowed)
      assert(responseAs[String] === "HTTP method not allowed, supported methods: GET")
    }
  }

  "leave GET requests to other paths unhandled" should "pass" in {
    Get("/kermit") ~> route ~> check {
      assert(!handled)
    }
  }
}
