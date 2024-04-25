package com.pieterv.down.domain.usecase

import kotlinx.coroutines.delay

class FetchInfoUseCaseImpl : FetchInfoUseCase {

    override suspend fun invoke(): List<ComplexData> {
        delay(3000L)
        return (1..100).map {
            ComplexData("Hello", "item count: $it")
        }
    }
}