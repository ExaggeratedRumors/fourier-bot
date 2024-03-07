package com.discord.fourierbot.commands

import net.dv8tion.jda.api.entities.Guild


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