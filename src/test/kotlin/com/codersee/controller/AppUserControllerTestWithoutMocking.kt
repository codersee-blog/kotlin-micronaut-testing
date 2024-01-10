package com.codersee.controller

import com.codersee.model.AppUser
import com.codersee.model.AppUserRequest
import com.codersee.util.extractAs
import com.codersee.util.whenever
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.restassured.common.mapper.TypeRef
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@MicronautTest
class AppUserControllerTestWithoutMocking {

  @Test
  fun `should return 200 OK on GET users`(spec: RequestSpecification) {
    spec
      .whenever()
      .get("/users")
      .then()
      .statusCode(200)
      .header("Content-Type", "application/json")
  }

  @Test
  fun `should return 404 Not Found on GET user by ID`(spec: RequestSpecification) {
    spec
      .whenever()
      .get("/users/123123123123123123121231")
      .then()
      .statusCode(404)
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

    spec
      .whenever()
      .contentType(ContentType.JSON)
      .body(request)
      .post("/users")
      .then()
      .statusCode(201)

    val list = spec
      .whenever()
      .get("/users")
      .then()
      .statusCode(200)
      .body("size()", `is`(1))
      .extractAs(object : TypeRef<List<AppUser>>() {})

    assertEquals(1, list.size)

    val createdUser = list.first()

    assertEquals(request.firstName, createdUser.firstName)
    assertEquals(request.street, createdUser.address.street)
  }

}