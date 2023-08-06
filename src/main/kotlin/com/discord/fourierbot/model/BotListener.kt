package com.discord.fourierbot.model

import com.discord.fourierbot.commands.Command
import com.discord.fourierbot.utils.Resources
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class BotListener(private val registeredCommands: Map<String, Command>): ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if(event.author.isBot) return
        val prefix = event.message.contentRaw.substring(
            0, Resources.configuration.prefix.length
        )
        if(prefix != Resources.configuration.prefix) return
        val message = event.message.contentRaw.drop(
            Resources.configuration.prefix.length
        )
        registeredCommands.forEach { (_, command) ->
            if(message.split(" ")[0] == command.call) {
                println("ENGINE: Attempt to execute command ${command.call}")
                command.execute(event.message)
            }
        }
    }
}