package org.jiwon.serverCore.events

import io.github.lunarjiwon.DiscordEmbedBuilder
import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.TextComponent
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.jiwon.serverCore.ServerCore.Companion.instance
import org.jiwon.serverCore.discord.DiscordWebhookManager.Companion.inGameWebhook
import java.awt.Color
import java.time.ZoneId
import java.time.ZonedDateTime

class ChatEvent:Listener {

    @EventHandler
    private fun chatEvent(event:AsyncChatEvent){
        Bukkit.getScheduler().runTaskAsynchronously(instance, Runnable {
            inGameWebhook
                .setEmbed(
                    DiscordEmbedBuilder()
                        .setTitle(event.player.name)
                        .setDescription((event.message() as TextComponent).content())
                        .setTimeStamp(ZonedDateTime.now(ZoneId.of("Asia/Seoul")))
                        .setColor(Color(99, 151, 255)).build()
                )
            inGameWebhook.execute()
        })

    }
}