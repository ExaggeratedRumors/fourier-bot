package com.discord.fourierbot.processing

import com.discord.fourierbot.utils.IMAGES_PATH
import com.discord.fourierbot.utils.ITEM_IMAGE_PATH
import com.discord.fourierbot.utils.Resources
import java.awt.Color
import java.awt.Font
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels
import java.nio.file.Files
import javax.imageio.ImageIO

class ImagesManager {
    fun createImageCopy(code: String) {
        val outputFolder = File(IMAGES_PATH)
        if (!outputFolder.exists()) outputFolder.mkdirs()

        try {
            val originalFile = File(ITEM_IMAGE_PATH)
            val copiedFile = File(outputFolder, originalFile.name)
            Files.copy(originalFile.toPath(), copiedFile.toPath())
            val image = ImageIO.read(copiedFile)
            val graphics = image.createGraphics()
            graphics.font = Font("Arial", Font.BOLD, 85)
            graphics.color = Color(255, 255, 255, 65)
            val rows = Resources.configuration.key_rows
            (0..rows).forEach {
                graphics.drawString(code, 0, image.height * (it + 1) / rows)
                graphics.drawString(code, image.width / 2, image.height * (it + 1) / rows)
            }

            graphics.dispose()
            val outputFile = File(outputFolder, "$code.png")
            ImageIO.write(image, "png", outputFile)

            println("ENGINE: Image has been created in ${outputFile.absolutePath}")
        } catch (e: Exception) {
            println("ENGINE: Image has not been created.")
            e.printStackTrace()
        }
    }

    fun deleteImageCopy(code: String) {
        try {
            val outputFolder = File(IMAGES_PATH)
            val copiedImage = File(outputFolder, code)
            copiedImage.delete()
        } catch (e: Exception) {
            println("ENGINE: Image could not be deleted.")
        }
    }

    fun downloadAndReplaceImage(url: String) {
        URL(url).openStream().use {
            Channels.newChannel(it).use { rbc ->
                FileOutputStream(ITEM_IMAGE_PATH).use { fos ->
                    fos.channel.transferFrom(rbc, 0, Long.MAX_VALUE)
                }
            }
        }
    }

    fun isImageExist(code: String): Boolean {
        val file = File("$IMAGES_PATH/$code.png")
        return file.exists()
    }
}