package com.discord.fourierbot.encryption

import com.discord.fourierbot.dto.UserEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.io.*


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
class CodesList {
    @JsonProperty
    val userEntities = mutableListOf<UserEntity>()

    @JsonIgnore
    fun modify(block: MutableList<UserEntity>.() -> Unit) {
        synchronized(userEntities) {
            block(userEntities)
        }
    }
}