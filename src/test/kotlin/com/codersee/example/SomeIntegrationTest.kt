package com.codersee.example

import io.micronaut.context.annotation.Requires
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@MicronautTest
@Requires(env = ["integration-test"])
class SomeIntegrationTest {

  @Test
  fun `should perform integration test`() {
    assertTrue(false)
  }

}