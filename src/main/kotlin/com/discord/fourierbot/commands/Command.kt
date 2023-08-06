package com.discord.fourierbot.commands
import net.dv8tion.jda.api.entities.Message

abstract class Command {
    var call = String()
    abstract fun execute(message: Message)
}