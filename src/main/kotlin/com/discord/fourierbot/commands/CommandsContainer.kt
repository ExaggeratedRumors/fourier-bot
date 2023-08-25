package com.discord.fourierbot.commands

class CommandsContainer {
    companion object {
        val implementedCommands: List<Command> = listOf (
            Leak(),
            Share(),
            Phrases(),
            Getter(),
            Upload()
        )
    }
}