package com.discord.fourierbot.model

import com.discord.fourierbot.commands.Command
import com.discord.fourierbot.utils.Resources
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class BotListener(private val registeredCommands: Map<String, Command>): ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot || event.message.contentRaw.length < 3) return
        val prefix = event.message.contentRaw.substring(
            0, Resources.configuration.prefix.length
        )
        val message = event.message.contentRaw.drop(
            Resources.configuration.prefix.length
        )
        val authorId = event.message.author.idLong

        if(!validate(message)) return

        registeredCommands.forEach { (_, command) ->
            if (commandsConditions(command, prefix, message, authorId)) {
                println("ENGINE: Attempt to execute command ${command.call}.")
                command.execute(event.message)
            }
        }
    }

    private fun validate(message: String): Boolean {
        Resources.illegalChars.forEach {
            if(message.contains(it)) return false
        }
        return true
    }

    private fun commandsConditions(command: Command, prefix: String, message: String, authorId: Long): Boolean {
        if(command.requirePrefix && prefix != Resources.configuration.prefix) return false
        if(command.requirePrefix && message.split(" ")[0] != command.call) return false
        else if(!command.requirePrefix && !prefix.plus(message).contains(command.call)) return false
        if(command.requireAdmin && authorId != Resources.configuration.admin_id) return false
        return true
    }
}