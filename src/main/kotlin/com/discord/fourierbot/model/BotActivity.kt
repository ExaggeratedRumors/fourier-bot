package com.discord.fourierbot.model

import com.discord.fourierbot.utils.Resources
import kotlin.system.exitProcess

class BotActivity {
    private val botBuilder = BotBuilder()

    fun startActivity() {
        reloadResources()
        try {
            botBuilder.registerCommands(Resources.commandsList)
            botBuilder.registerListener()
            botBuilder.build(Resources.configuration)
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            exitProcess(1)
        }
    }

    private fun reloadResources() {
        Resources.loadResources()
    }
}