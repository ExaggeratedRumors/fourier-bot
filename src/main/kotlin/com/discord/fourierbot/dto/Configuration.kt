package com.discord.fourierbot.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Configuration (
    val client: ClientData = ClientData(),
    val prefix: String = String(),
    val key_length: Int = 10
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
data class ClientData (
    val client_name: String = String(),
    val client_id: String = String(),
    val client_secret: String = String(),
    val public_key: String = String(),
    val token: String = String(),
    val permission_integer: Int = 0
)