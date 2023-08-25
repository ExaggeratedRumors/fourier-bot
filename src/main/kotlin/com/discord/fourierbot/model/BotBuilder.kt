package com.discord.fourierbot.model

import com.discord.fourierbot.commands.Command
import com.discord.fourierbot.commands.CommandsContainer
import com.discord.fourierbot.dto.CommandsList
import com.discord.fourierbot.dto.Configuration
import net.dv8tion.jda.api.JDA

import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import java.lang.IllegalStateException

class BotBuilder {
    private var jda: JDA? = null
    private var registeredCommands: HashMap<String, Command>? = null
    private var listener: BotListener? = null

    fun build(configuration: Configuration) {
        jda = JDABuilder
            .createDefault(configuration.client.token)
            .enableIntents(GatewayIntent.DIRECT_MESSAGES)
            .enableIntents(GatewayIntent.MESSAGE_CONTENT)
            .addEventListeners(
                listener
                ?: throw IllegalStateException("ENGINE: Listener has not been initialized.")
            )
            .build()
            .awaitReady()
        println("ENGINE: Bot has been launched.")
    }

    fun registerCommands(commandsList: CommandsList) {
        registeredCommands = HashMap()
        CommandsContainer.implementedCommands.forEach {
            if(commandsList.commands[it.javaClass.simpleName]?.enabled == true) {
                registeredCommands!![it.javaClass.simpleName] = it
                it.call = commandsList.commands[it.javaClass.simpleName]!!.call
                it.requirePrefix = commandsList.commands[it.javaClass.simpleName]!!.prefix
                println("ENGINE: Command ${it.call} installed.")
            }
        }
    }

    fun registerListener() {
        listener = BotListener(
            registeredCommands
                ?: throw IllegalStateException("ENGINE: Commands has not been initialized.")
        )
    }
}