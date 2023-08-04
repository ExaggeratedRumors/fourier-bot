package com.discord.fourierbot.model

import com.discord.fourierbot.utils.Resources
import dev.kord.cache.map.MapLikeCollection
import dev.kord.cache.map.internal.MapEntryCache
import dev.kord.cache.map.lruLinkedHashMap
import dev.kord.common.annotation.KordPreview
import dev.kord.core.Kord
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on


class BotBuilder {
    lateinit var kord: Kord

    suspend fun authorizeBot() {
        kord = Kord(Resources.configuration.client.token) {
            cache {
                users { cache, description ->
                    MapEntryCache(cache, description, MapLikeCollection.concurrentHashMap())
                }

                messages { cache, description ->
                    MapEntryCache(cache, description, MapLikeCollection.lruLinkedHashMap(maxSize = 100))
                }

                members { cache, description ->
                    MapEntryCache(cache, description, MapLikeCollection.none())
                }
            }
        }
    }

    fun registerCommands() {
        kord.on<MessageCreateEvent> {
            if(message.content == "!ping") return@on

            //get the behavior of a channel for free, happy!
            message.channel.createMessage("pong!")
        }
    }
}