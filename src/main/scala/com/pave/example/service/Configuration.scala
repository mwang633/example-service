package com.example.service

import java.io.File

import com.typesafe.config.{ConfigFactory, Config}
import org.apache.logging.log4j.LogManager

class Configuration() {
  private val log = LogManager.getLogger(classOf[Configuration])

  val config: Config = {
    val configDefaults = ConfigFactory.load(this.getClass.getClassLoader, "application.conf")

    scala.sys.props.get("production.conf") match {
      case Some(filename) =>
        val configFile = new File(filename)
        assert(configFile.canRead)
        log.info(s"loading $configFile as config")
        ConfigFactory.parseFile(configFile).withFallback(configDefaults)
      case None =>
        log.info("no config override found, use default as config")
        configDefaults
    }
  }

  object ExampleRemoteServer {
    val url: String = config.getString("ExampleRemoteServer.url")
  }

}
