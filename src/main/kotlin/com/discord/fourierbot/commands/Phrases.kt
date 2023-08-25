package com.discord.fourierbot.commands

import com.discord.fourierbot.utils.Resources
import net.dv8tion.jda.api.entities.Message

class Phrases : Command() {
    override fun execute(message: Message) {
        message.channel
            .sendMessage(Resources.phrasesList.phrases.random())
            .queue()
    }
}