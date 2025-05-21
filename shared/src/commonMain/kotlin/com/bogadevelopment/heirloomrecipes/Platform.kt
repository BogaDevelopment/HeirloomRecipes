package com.bogadevelopment.heirloomrecipes

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform