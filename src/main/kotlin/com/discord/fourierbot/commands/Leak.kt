package com.discord.fourierbot.commands

import net.dv8tion.jda.api.entities.Message

class Leak : Command() {
    override fun execute(message: Message) {
        message.author
            .openPrivateChannel()
            .complete()
            .sendMessage("ty szczurze jebany")
            .queue()
    }
}