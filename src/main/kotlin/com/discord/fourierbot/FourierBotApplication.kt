package com.discord.fourierbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FourierBotApplication

fun main(args: Array<String>) {
    runApplication<FourierBotApplication>(*args)
}
