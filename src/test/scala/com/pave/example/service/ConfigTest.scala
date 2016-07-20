package com.example.service

import org.scalatest.FlatSpec
import org.scalatest.Matchers._

class ConfigTest extends FlatSpec {

  "Config Test" should "pass" in {
    val config = new Configuration()

    assert(config.ExampleRemoteServer.url != null)
    assert(config.ExampleRemoteServer.url != "")
  }
}
