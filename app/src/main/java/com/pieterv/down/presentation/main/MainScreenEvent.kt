package com.pieterv.down.presentation.main

sealed class MainScreenEvent {

    data object LoadData : MainScreenEvent()

}