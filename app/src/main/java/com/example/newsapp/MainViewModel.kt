package com.example.newsapp

import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.useCases.app_entry.AppEntryUseCases
import com.example.newsapp.presentation.navGraph.ScreenRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
     appEntryUseCases: AppEntryUseCases
): ViewModel() {

    var splashCondition by mutableStateOf(true)//state tracking the splash screen ability to be seen
        private set

    var startDestination by mutableStateOf(ScreenRoute.AppStartNavigation.route)//this tracks route the screen should start
        private set

    init {
        appEntryUseCases.readAppEntry().onEach { shouldStartFromHomeScreen ->
            // this using the appEntry readAppEntry value decides where to show splash and onBoarding screen o the NewsApp right away
            startDestination = if(shouldStartFromHomeScreen){
                ScreenRoute.NewsNavigation.route
            } else {
                ScreenRoute.AppStartNavigation.route
            }
            delay(500)
            splashCondition = false
        }.launchIn(viewModelScope)
    }

}