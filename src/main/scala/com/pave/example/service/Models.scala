package com.example.service

import java.sql.Timestamp
import spray.json._

case class ExampleDataRow(userId : String,
                          userInfo1 : Option[Int],
                          userInfo2 : String,
                          userInfoTime : Timestamp)


object JsonSerializeProtocol extends DefaultJsonProtocol {
  implicit object TimestampFormat extends RootJsonFormat[Timestamp] {
    def write(c: Timestamp) = JsString(c.toString)

    def read(value: JsValue) = value match {
      case s: JsString => Timestamp.valueOf(s.value)
      case _ => deserializationError("Timestamp expected")
    }
  }

  implicit val exampleDataRowFormatter = jsonFormat4(ExampleDataRow)
}
