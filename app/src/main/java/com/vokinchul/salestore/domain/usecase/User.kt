package com.vokinchul.salestore.domain.usecase

import com.vokinchul.salestore.domain.model.AuthToken
import com.vokinchul.salestore.domain.model.User
import com.vokinchul.salestore.domain.repository.FakeStoreRepository
import jakarta.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: FakeStoreRepository
) {
    suspend operator fun invoke(username: String, password: String): AuthToken {
        return repository.login(username, password)
    }
}

class GetUserUseCase @Inject constructor(
    private val repository: FakeStoreRepository
) {
    suspend operator fun invoke(id: Int): User {
        return repository.getUser(id)
    }
}