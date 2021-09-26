package com.example.filmjetpacksub2.utils

import androidx.test.espresso.idling.CountingIdlingResource

object IdlingResource {
    private const val RESOURCE = "GLOBAL"
    private val idlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        idlingResource.increment()
    }

    fun decrement() {
        idlingResource.decrement()
    }

    fun getIdlingResource(): CountingIdlingResource = idlingResource
}