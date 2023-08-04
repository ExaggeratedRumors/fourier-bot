package com.discord.fourierbot.model

import com.discord.fourierbot.utils.Resources
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

class BotActivity {
    private val botBuilder = BotBuilder()

    fun startActivity() {
        loadResources()
        registerBot()
    }

    private fun loadResources() {
        Resources.loadResources()
    }

    private fun registerBot() {
        runBlocking {
            botBuilder.authorizeBot()
            botBuilder.registerCommands()
        }
    }
}