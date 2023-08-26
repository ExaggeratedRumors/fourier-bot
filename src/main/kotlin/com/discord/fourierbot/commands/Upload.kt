package com.discord.fourierbot.commands

import com.discord.fourierbot.encryption.CodesManager
import com.discord.fourierbot.processing.ImagesManager
import net.dv8tion.jda.api.entities.Message

class Upload : Command() {
    override fun execute(message: Message) {
        if(message.attachments.first().contentType != "image/png") {
            message.author
                .openPrivateChannel()
                .complete()
                .sendMessage("File have not been found.")
                .queue()
            return
        }
        ImagesManager().downloadAndReplaceImage(message.attachments.first().url)
        CodesManager().clearUsersList()

        message.author
            .openPrivateChannel()
            .complete()
            .sendMessage("Image replaced and users list is empty.")
            .queue()
    }
}