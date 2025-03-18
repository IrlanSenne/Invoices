package com.e_conomic.invoice.usecases

interface BaseUseCase<in Input, out Output> {
    suspend fun execute(input: Input): Output
}