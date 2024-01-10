package com.codersee.model

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity

@MappedEntity
data class AppUser(
  @field:Id
  @field:GeneratedValue
  val id: String? = null,
  val firstName: String,
  val lastName: String,
  val email: String,
  val address: Address
)