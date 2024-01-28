package com.thinh.flashcardlearning

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform