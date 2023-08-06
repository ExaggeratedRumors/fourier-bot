package com.discord.fourierbot.utils

import com.discord.fourierbot.dto.CommandsList
import com.discord.fourierbot.dto.Configuration

class Resources {
    companion object {
        var configuration: Configuration = Configuration()
        var commandsList: CommandsList = CommandsList()

        fun loadResources() {
            configuration = YamlReader.readYamlObject(CONFIGURATION_PATH, Configuration::class.java)
            commandsList = YamlReader.readYamlObject(COMMANDS_PATH, CommandsList::class.java)
        }
    }
}