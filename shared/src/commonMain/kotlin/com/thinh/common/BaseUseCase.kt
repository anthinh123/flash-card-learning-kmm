package com.thinh.common

interface BaseUseCase<Input, Output> {
    suspend fun execute(input: Input): Output
}