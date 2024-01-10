package com.codersee.service

import com.codersee.repository.AppUserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class AppUserServiceTest {

  private val appUserRepository = mockk<AppUserRepository>()

  private val appUserService = AppUserService(appUserRepository)

  @Test
  fun `should return empty user list`() {
    every {
      appUserRepository.findAll()
    } returns emptyList()

    val result = appUserService.findAll()

    assertTrue(result.isEmpty())

    verify(exactly = 1) { appUserRepository.findAll() }
  }
}