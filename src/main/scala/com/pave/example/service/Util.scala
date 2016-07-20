package com.example.service

import java.sql.Timestamp

import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat
import spray.http.{DateTime => SprayDateTime}

object Util {

  implicit class TimestampEx(ts: Timestamp) {
    def toDateTime = new DateTime(ts.getTime)
  }

  implicit class DateTimeEx(dateTime: DateTime) {
    def toTimestamp = new Timestamp(dateTime.getMillis)

    def toSprayDateTime = SprayDateTime(dateTime.getMillis)

    def truncDay() = dateTime.withMillisOfDay(0)
  }

  def parseDateTime(s: String): DateTime = ISODateTimeFormat.dateTimeParser().parseDateTime(s)
}
