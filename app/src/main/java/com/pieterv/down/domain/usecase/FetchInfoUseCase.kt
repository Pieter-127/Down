package com.pieterv.down.domain.usecase

interface FetchInfoUseCase {

    suspend operator fun invoke(): List<ComplexData>
}