package com.discord.fourierbot.encryption

import com.discord.fourierbot.dto.UserEntity
import java.io.*


class CodesList : Serializable {
    val userEntities = mutableListOf<UserEntity>()

    fun modify(block: MutableList<UserEntity>.() -> Unit) {
        synchronized(userEntities) {
            block(userEntities)
        }
    }
}