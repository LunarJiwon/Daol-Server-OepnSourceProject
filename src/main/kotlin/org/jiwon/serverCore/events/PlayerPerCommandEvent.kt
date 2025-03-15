package org.jiwon.serverCore.events

import io.github.lunarjiwon.DiscordEmbedBuilder
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.jiwon.serverCore.ServerCore.Companion.instance
import org.jiwon.serverCore.discord.DiscordWebhookManager.Companion.inGameWebhook
import org.jiwon.serverCore.discord.DiscordWebhookManager.Companion.manageWebhook
import org.jiwon.serverCore.events.warp.PlayerWarpEvent
import java.awt.Color
import java.time.ZoneId
import java.time.ZonedDateTime

class PlayerPerCommandEvent:Listener {

    @EventHandler
    private fun playerPerCommandEvent(event:PlayerCommandPreprocessEvent){
        Bukkit.getScheduler().runTaskAsynchronously(instance, Runnable {
            manageWebhook
                .setEmbed(
                    DiscordEmbedBuilder()
                        .setTitle(event.player.name)
                        .setDescription(event.message)
                        .setTimeStamp(ZonedDateTime.now(ZoneId.of("Asia/Seoul")))
                        .setColor(Color(99, 151, 255)).build()
                )
            manageWebhook.execute()
        })
        PlayerWarpEvent(event,event.player,event.message.split("/")[1])
    }

}