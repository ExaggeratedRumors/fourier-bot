package com.discord.fourierbot

import com.discord.fourierbot.encryption.CodesManager
import com.discord.fourierbot.processing.ImagesManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import java.io.File

class UnitTest {

    @Test
    fun codeGenerator() {
        val codesManager = CodesManager()
        val codes = listOf(
            codesManager.generateKey(10),
            codesManager.generateKey(10),
            codesManager.generateKey(10)
        )
        println(codes)
        assertNotEquals(codes[0], codes[1])
    }

    @Test
    fun imageCreator() {
        val code = "234dg23wsa213"
        val imageManager = ImagesManager()
        imageManager.createImageCopy(code)
        //assertEquals(true, imageManager.isImageExist(code))
    }


}