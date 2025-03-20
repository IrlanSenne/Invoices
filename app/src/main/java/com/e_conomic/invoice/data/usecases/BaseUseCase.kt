package com.e_conomic.invoice.data.usecases

interface BaseUseCase<in Input, out Output> {
    suspend fun execute(input: Input): Output
}