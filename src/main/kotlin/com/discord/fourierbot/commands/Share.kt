package com.discord.fourierbot.commands

import com.discord.fourierbot.encryption.CodesManager
import com.discord.fourierbot.processing.ImagesManager
import com.discord.fourierbot.utils.IMAGES_PATH
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.utils.FileUpload
import java.io.File

class Share : Command() {
    override fun execute(message: Message) {
        val userCode = CodesManager().getUserCodeById(message.author.idLong)
            ?: createNewUserEntity(message.author.idLong, message.author.name)

        message
            .author
            .openPrivateChannel()
            .complete()
            .sendMessage("Zrzut aktualnie analizowanych przedmiotów:")
            .addFiles(FileUpload.fromData(File("$IMAGES_PATH/$userCode.png")))
            .queue()
    }

    private fun createNewUserEntity(userId: Long, userName: String): String {
        val code = CodesManager().addEntity(userId, userName)
        ImagesManager().createImageCopy(code)
        return code
    }
}