package com.discord.fourierbot.commands

import com.discord.fourierbot.encryption.CodesManager
import com.discord.fourierbot.utils.Resources
import net.dv8tion.jda.api.entities.Message

class Getter : Command() {
    override fun execute(message: Message) {
        if(message.author.idLong != Resources.configuration.admin_id) {
            println("ENGINE: Attempt to cheat admin-mode command.")
            return
        }
        val subcommand = message
            .contentRaw
            .drop(Resources.configuration.prefix.length + call.length + 1)
        if(subcommand.isEmpty()) sendUsersList(message)
        else if(subcommand.length == Resources.configuration.key_length) sendUserName(message, subcommand)
        else sendUserCode(message, subcommand)
    }

    private fun sendUsersList(message: Message) {
        var usersList = CodesManager().getUsersList()
        if(usersList.isEmpty()) usersList = "User list is empty."
        message
            .author
            .openPrivateChannel()
            .complete()
            .sendMessage("```\n$usersList\n```")
            .queue()
    }

    private fun sendUserName(message: Message, userCode: String) {
        val userName = CodesManager().getUserNameByCode(userCode)
        val alert = userName?.let {"Wykryłem użytkownika $userName"}
            ?: "Nie znalazłem użytkownika"

        message
            .author
            .openPrivateChannel()
            .complete()
            .sendMessage(alert)
            .queue()
    }

    private fun sendUserCode(message: Message, userId: String) {
        val userCode = CodesManager().getUserCodeById(userId.toLong())
        val alert = userCode?.let {"Znalazłem kod użytkownika: $userCode"}
            ?: "Nie znalazłem kodu dla tego użytkownika"

        message
            .author
            .openPrivateChannel()
            .complete()
            .sendMessage(alert)
            .queue()
    }
}