package com.dreyesyho.myapplication.navigation

sealed interface ContentType {
    object List : ContentType
    object ListAndDetail : ContentType
}