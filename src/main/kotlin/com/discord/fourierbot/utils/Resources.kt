package com.discord.fourierbot.utils

import com.discord.fourierbot.dto.CommandsList
import com.discord.fourierbot.dto.Configuration
import com.discord.fourierbot.dto.PhrasesList
import com.discord.fourierbot.encryption.CodesList
import com.discord.fourierbot.encryption.CodesManager

class Resources {
    companion object {
        const val keyAlgorithm: String = "HmacSHA384"
        var configuration: Configuration = Configuration()
        var commandsList: CommandsList = CommandsList()
        var phrasesList: PhrasesList = PhrasesList()
        var keys: CodesList = CodesList()

        fun loadResources() {
            configuration = YamlReader.readYamlObject(CONFIGURATION_PATH, Configuration::class.java)
            commandsList = YamlReader.readYamlObject(COMMANDS_PATH, CommandsList::class.java)
            phrasesList = YamlReader.readYamlObject(PHRASES_PATH, PhrasesList::class.java)
            keys = CodesManager().deserialize()
        }
    }
}