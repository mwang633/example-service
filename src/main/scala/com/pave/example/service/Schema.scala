package com.example.service

object Schema {

  import java.sql.Timestamp

  import slick.driver.PostgresDriver.api._

  class ExampleTable(tag: Tag) extends Table[ExampleDataRow](tag, "yodlee_users") {
    def userId = column[String]("user_id", O.PrimaryKey)
    def userInfo1 = column[Option[Int]]("user_info_1")
    def userInfo2 = column[String]("user_info_2")
    def userInfoTime = column[Timestamp]("user_info_time")

    def * = (userId, userInfo1, userInfo2, userInfoTime) <>(ExampleDataRow.tupled, ExampleDataRow.unapply)
  }

  val exampleTable = TableQuery[ExampleTable]
}
