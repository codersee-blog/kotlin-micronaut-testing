package com.codersee.model

import io.micronaut.serde.annotation.Serdeable

@Serdeable.Serializable
@Serdeable.Deserializable
data class Address(
  val street: String,
  val city: String,
  val code: Int
)