package com.codersee.controller

import com.codersee.model.Address
import com.codersee.model.AppUser
import com.codersee.model.AppUserRequest
import com.codersee.repository.AppUserRepository
import com.codersee.util.extractAs
import com.codersee.util.whenever
import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import jakarta.inject.Inject
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

@MicronautTest
class AppUserControllerTestWithMocking {

  @Inject
  private lateinit var context: ApplicationContext

  @Inject
  private lateinit var server: EmbeddedServer

  private val repo: AppUserRepository = mockk<AppUserRepository>()

  @Test
  fun `should return 200 OK on GET users`(spec: RequestSpecification) {
    every {
      repo.findAll()
    } returns emptyList()

    spec
      .whenever()
      .get("/users")
      .then()
      .statusCode(200)
      .header("Content-Type", "application/json")
  }

  @Test
  fun `should return user by ID`(spec: RequestSpecification) {
    val foundUser = AppUser(
      id = "123",
      firstName = "Piotr",
      lastName = "Wolak",
      email = "contact@codersee.com",
      address = Address(
        street = "street",
        city = "city",
        code = 123
      )
    )

    every {
      repo.findById("123")
    } returns Optional.of(foundUser)

    spec
      .whenever()
      .get("/users/123")
      .then()
      .statusCode(200)
      .body("id", equalTo("123"))
      .body("firstName", equalTo("Piotr"))
      .body("address.street", equalTo("street"))
      .body("address.code", equalTo(123))
  }

  @Test
  fun `should return user by ID and verify with extract`(spec: RequestSpecification) {
    val foundUser = AppUser(
      id = "123",
      firstName = "Piotr",
      lastName = "Wolak",
      email = "contact@codersee.com",
      address = Address(
        street = "street",
        city = "city",
        code = 123
      )
    )

    every {
      repo.findById("123")
    } returns Optional.of(foundUser)

    val extracted = spec
      .whenever()
      .get("/users/123")
      .then()
      .statusCode(200)
      .extractAs(AppUser::class.java)

    assertEquals(foundUser, extracted)
  }

  @Test
  fun `should create a user`(spec: RequestSpecification) {
    val request = AppUserRequest(
      firstName = "Piotr",
      lastName = "Wolak",
      email = "contact@codersee.com",
      street = "Street",
      city = "City",
      code = 123,
    )

    val createdUser = AppUser(
      id = "123",
      firstName = "Piotr",
      lastName = "Wolak",
      email = "contact@codersee.com",
      address = Address(
        street = "street",
        city = "city",
        code = 123
      )
    )

    every {
      repo.save(any())
    } returns createdUser

    val extracted = spec
      .whenever()
      .contentType(ContentType.JSON)
      .body(request)
      .post("/users")
      .then()
      .statusCode(201)
      .extractAs(AppUser::class.java)

    assertEquals(createdUser, extracted)
  }

  @MockBean(AppUserRepository::class)
  fun appUserRepository(): AppUserRepository = repo
}