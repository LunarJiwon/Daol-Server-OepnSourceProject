package org.jiwon.serverCore.events

import io.github.lunarjiwon.DiscordEmbedBuilder
import net.kyori.adventure.text.TextComponent
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.jiwon.serverCore.ServerCore.Companion.instance
import org.jiwon.serverCore.discord.DiscordWebhookManager.Companion.inGameWebhook
import java.awt.Color
import java.time.ZoneId
import java.time.ZonedDateTime

class DeathEvent:Listener {

    @EventHandler
    private fun deathEvent(event:PlayerDeathEvent){
//        event.
        Bukkit.getScheduler().runTaskAsynchronously(instance, Runnable {
            inGameWebhook
                .setEmbed(
                    DiscordEmbedBuilder()
                        .setTitle("플레이어 사망")
                        .setDescription("${event.player.name}(이)가 ${event.damageSource.causingEntity?.name}에 의해 죽었습니다.")
                        .setTimeStamp(ZonedDateTime.now(ZoneId.of("Asia/Seoul")))
                        .setColor(Color(235, 64, 52)).build()
                )
            inGameWebhook.execute()
        })
    }

}