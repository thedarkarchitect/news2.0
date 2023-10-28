package com.example.newsapp.domain.useCases.app_entry

import com.example.newsapp.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class ReadAppEntry (
    private val localUserManager: LocalUserManager
) {
    operator fun invoke(): Flow<Boolean> {
       return localUserManager.readAppEntry()
    }
}