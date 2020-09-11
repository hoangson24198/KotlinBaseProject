package com.atmaneuler.hsdps.base

import com.example.firebaseStarterKit.base.Result
import java.lang.Error

abstract class BaseUseCase<T>() {
    abstract suspend fun create(data: Map<String, Any>? = null):Result<T,Error>
}