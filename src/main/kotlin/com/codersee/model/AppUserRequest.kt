package com.codersee.model

import io.micronaut.serde.annotation.Serdeable

@Serdeable.Deserializable
data class AppUserRequest(
  val firstName: String,
  val lastName: String,
  val email: String,
  val street: String,
  val city: String,
  val code: Int
)