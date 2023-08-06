package com.discord.fourierbot.commands

import com.discord.fourierbot.utils.FILE_PATH
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.utils.FileUpload
import java.io.File

class Share : Command() {
    override fun execute(message: Message) {
        message
            .author
            .openPrivateChannel()
            .complete()
            .sendMessage("Aktualnie brak analizowanych przedmiotów")
            .addFiles(FileUpload.fromData(File(FILE_PATH)))
            .queue()
    }

}