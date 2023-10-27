package com.example.newsapp.domain.manager.useCases

import com.example.newsapp.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class ReadAppEntry (
    private val localUserManager: LocalUserManager
) {
    operator fun invoke(): Flow<Boolean> {
       return localUserManager.readAppEntry()
    }
}