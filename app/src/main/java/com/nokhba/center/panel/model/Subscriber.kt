package com.nokhba.center.panel.model

data class Subscriber(
    val firstName: String? = null,
    val lastName: String? = null,
    val birth: String? = null,
    val address: String? = null,
    val mobile: String? = null,
    val icn: String? = null,
    val genre: String? = null,
    val job: String? = null,
    val study: String? = null,
    val paid: Boolean = false,
    val formation: String? = null,
    val group: String? = null,
    val dateRegistration: String? = null
)