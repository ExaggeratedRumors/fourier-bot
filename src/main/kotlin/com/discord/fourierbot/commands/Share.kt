package com.discord.fourierbot.commands

import com.discord.fourierbot.encryption.CodesManager
import com.discord.fourierbot.processing.ImagesManager
import com.discord.fourierbot.utils.IMAGES_PATH
import com.discord.fourierbot.utils.MAIN_IMAGE_NAME
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.utils.FileUpload
import java.io.File

class Share : Command() {
    override fun execute(message: Message) {
        val imageName = message.contentRaw.split(" ").getOrNull(1) ?: MAIN_IMAGE_NAME
        val userCode = CodesManager().getUserCodeById(message.author.idLong)
            ?: CodesManager().addEntity(message.author.idLong, message.author.name)

        ImagesManager().getOrCreateImage(imageName, userCode)?.let {
            message
                .author
                .openPrivateChannel()
                .complete()
                .sendMessage("Zrzut aktualnie analizowanych przedmiotów:")
                .addFiles(FileUpload.fromData(it))
                .queue()
        }

    }
}