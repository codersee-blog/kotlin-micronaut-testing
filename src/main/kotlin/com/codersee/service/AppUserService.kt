package com.codersee.service

import com.codersee.model.Address
import com.codersee.model.AppUser
import com.codersee.model.AppUserRequest
import com.codersee.repository.AppUserRepository
import io.micronaut.http.HttpStatus
import io.micronaut.http.exceptions.HttpStatusException
import jakarta.inject.Singleton

@Singleton
class AppUserService(
  private val appUserRepository: AppUserRepository
) {

  fun create(userRequest: AppUserRequest): AppUser =
    appUserRepository.save(
      userRequest.toAppUserEntity()
    )

  fun findAll(): List<AppUser> =
    appUserRepository
      .findAll()
      .toList()

  fun findById(id: String): AppUser =
    appUserRepository.findById(id)
      .orElseThrow { HttpStatusException(HttpStatus.NOT_FOUND, "User with id: $id was not found.") }

  fun update(id: String, updateRequest: AppUserRequest): AppUser {
    val foundUser = findById(id)

    val updatedEntity =
      updateRequest
        .toAppUserEntity()
        .copy(id = foundUser.id)

    return appUserRepository.update(updatedEntity)
  }

  fun deleteById(id: String) {
    val foundUser = findById(id)

    appUserRepository.delete(foundUser)
  }

  fun findByNameLike(name: String): List<AppUser> =
    appUserRepository
      .findByFirstNameLike(name)

  private fun AppUserRequest.toAppUserEntity(): AppUser {
    val address = Address(
      street = this.street,
      city = this.city,
      code = this.code
    )

    return AppUser(
      id = null,
      firstName = this.firstName,
      lastName = this.lastName,
      email = this.email,
      address = address
    )
  }
}