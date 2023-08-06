package com.discord.fourierbot.encryption

import com.discord.fourierbot.dto.UserEntity

class CodesList {
    val userEntities = mutableListOf<UserEntity>()

    fun modify(block: MutableList<UserEntity>.() -> Unit) {
        synchronized(userEntities) {
            block(userEntities)
        }
    }
}