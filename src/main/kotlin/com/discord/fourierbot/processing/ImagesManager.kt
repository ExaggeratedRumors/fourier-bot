package com.discord.fourierbot.processing

import com.discord.fourierbot.utils.IMAGES_PATH
import com.discord.fourierbot.utils.MAIN_IMAGE_NAME
import com.discord.fourierbot.utils.Resources
import java.awt.Color
import java.awt.Font
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels
import java.nio.file.Files
import javax.imageio.ImageIO
import kotlin.math.ceil
import kotlin.math.roundToInt

class ImagesManager {
    fun createImageCopy(imageName: String, code: String) {
        val outputFolder = File(IMAGES_PATH)
        if (!outputFolder.exists()) outputFolder.mkdirs()

        try {
            val originalFile = File(IMAGES_PATH, "$imageName.png")
            val copiedFile = File(outputFolder, originalFile.name)
            Files.copy(originalFile.toPath(), copiedFile.toPath())
            val image = ImageIO.read(copiedFile)
            val graphics = image.createGraphics()
            graphics.font = Font("Arial", Font.BOLD, Resources.configuration.key_rows)
            graphics.color = Color(255, 255, 255, 50)
            graphics.rotate(0.45)
            val rows = ceil(1.0 * image.height / Resources.configuration.key_rows).toInt()
            val columns = ceil(1.0 * image.width / (code.length * Resources.configuration.key_rows)).toInt()
            (-rows until rows).forEach { y ->
                (0 until 2 * columns).forEach { x ->
                    graphics.drawString(
                        code,
                        (0.85 * image.width * x / columns).roundToInt(),
                        (1.0 * image.height * y / rows).roundToInt()
                    )
                }
            }

            graphics.dispose()
            val outputFile = File(outputFolder, "${imageName}_${code}.png")
            ImageIO.write(image, "png", outputFile)

            println("ENGINE: Image has been created in ${outputFile.absolutePath}")
        } catch (e: Exception) {
            println("ENGINE: Image has not been created.")
            e.printStackTrace()
        }
    }

    fun getUserImage(imageName: String, code: String): File? {
        if(!isImageExist(imageName, code)) return null
        return File(IMAGES_PATH, "${imageName}_${code}.png")
    }

    fun getOrCreateImage(imageName: String, code: String): File? {
        if(!isImageExist(imageName, code)) createImageCopy(imageName, code)
        return File(IMAGES_PATH, "${imageName}_${code}.png")
    }

    fun deleteImageCopy(imageName:String, code: String) {
        try {
            val outputFolder = File(IMAGES_PATH)
            val copiedImage = File(outputFolder, "${imageName}_${code}.png")
            copiedImage.delete()
        } catch (e: Exception) {
            println("ENGINE: Image could not be deleted.")
        }
    }

    fun downloadAndReplaceImage(url: String) {
        URL(url).openStream().use {
            Channels.newChannel(it).use { rbc ->
                FileOutputStream("$IMAGES_PATH/$MAIN_IMAGE_NAME.png").use { fos ->
                    fos.channel.transferFrom(rbc, 0, Long.MAX_VALUE)
                }
            }
        }
    }

    fun isImageExist(imageName: String, code: String): Boolean {
        val file = File("$IMAGES_PATH/${imageName}_${code}.png")
        return file.exists()
    }
}