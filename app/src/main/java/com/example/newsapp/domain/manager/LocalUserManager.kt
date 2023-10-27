package com.example.newsapp.domain.manager

import kotlinx.coroutines.flow.Flow

interface LocalUserManager { //this interface will be used by the data preference class to dettermine show of onboard screen o just enter the app

    suspend fun saveAppEntry()//this will save the preference value so that it always read by the app

    fun readAppEntry(): Flow<Boolean> //And this will change the value of the preference value the flow comes from the dataStore preference value
}