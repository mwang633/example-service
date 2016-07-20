val akkaV = "2.4.3"
val sprayV = "1.3.3"
val slickV = "3.1.1"

lazy val `example-service` = (project in file(".")).
  settings(
    name := "example-service",
    version := "1.0",
    scalaVersion := "2.11.8",
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-encoding", "utf8"),
    libraryDependencies ++= Seq(
        "io.spray"            %%   "spray-servlet"     % sprayV,
        "io.spray"            %%   "spray-routing"     % sprayV,
        "io.spray"            %%   "spray-client"      % sprayV,
        "io.spray"            %%   "spray-util"        % sprayV,
        "io.spray"            %%   "spray-caching"     % sprayV,
        "io.spray"            %%   "spray-can"         % sprayV,
        "io.spray"            %%   "spray-testkit"     % sprayV % "test",

        "io.spray"            %%   "spray-json"        % "1.3.1",

        "com.typesafe.akka"   %%   "akka-actor"         % akkaV,
        "com.typesafe.akka"   %%   "akka-testkit"       % akkaV,
        "com.typesafe.akka"   %%   "akka-testkit"       % akkaV % "test",

        "joda-time"           %    "joda-time"          % "2.9.2",

        "com.typesafe.slick"  %%   "slick"              % slickV,
        "com.typesafe.slick"  %%   "slick-codegen"      % slickV,

        "org.postgresql"      %    "postgresql"         % "9.4.1207.jre7",
        "org.scalatest"       %%   "scalatest"          % "2.2.6" % "test",
        "org.apache.kafka"    %    "kafka-clients"       % "0.8.2.2",

        "org.apache.logging.log4j" % "log4j-core"       % "2.5",
        "org.apache.logging.log4j" % "log4j-api"        % "2.5",

        "de.heikoseeberger"   %% "akka-log4j" % "1.1.3"
      )
  )

assemblyJarName in assembly := s"${name.value}-${git.gitHeadCommit.value.get}.jar"