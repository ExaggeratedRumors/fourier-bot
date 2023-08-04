package com.discord.fourierbot.utils

import com.discord.fourierbot.dto.Configuration

class Resources {
    companion object {
        var configuration: Configuration = Configuration()

        fun loadResources() {
            configuration = YamlReader.readYamlObject(CONFIGURATION_PATH, Configuration::class.java)
        }
    }
}