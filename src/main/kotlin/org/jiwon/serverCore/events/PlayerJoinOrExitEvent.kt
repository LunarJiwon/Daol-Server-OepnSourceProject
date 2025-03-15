package org.jiwon.serverCore.events


import io.github.lunarjiwon.DiscordEmbed
import io.github.lunarjiwon.DiscordEmbedBuilder
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.jiwon.serverCore.ServerCore.Companion.defaultConfig
import org.jiwon.serverCore.ServerCore.Companion.gson
import org.jiwon.serverCore.ServerCore.Companion.instance
import org.jiwon.serverCore.ServerCore.Companion.playerDataFile
import org.jiwon.serverCore.alternative.ReplaceTexts
import org.jiwon.serverCore.alternative.ConfigNames
import org.jiwon.serverCore.discord.DiscordWebhookManager
import org.jiwon.serverCore.discord.DiscordWebhookManager.Companion.inGameWebhook
import org.jiwon.serverCore.manager.BalanceManager
import org.jiwon.serverCore.manager.LocationManager
import org.jiwon.serverCore.messages.DiscordMessages
import org.jiwon.serverCore.scoreboard.ScoreboardManager
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.LocationData
import java.awt.Color
import java.time.ZoneId
import java.time.ZonedDateTime

class PlayerJoinOrExitEvent : Listener {

    @EventHandler
    private fun playerJoin(event:PlayerJoinEvent){
        sendJoinMessage(event)


        if(playerDataFile.getRawData(event.player.uniqueId.toString()) == null) playerDataFile.set(event.player.uniqueId.toString(),event.player.name)

        if(!BalanceManager.isExistsPlayerBalance(event.player)){
            // 환영메시지 출력
            event.player.server.broadcast(mm(defaultConfig.getString(ConfigNames.PLAYER_FIRST_JOIN_MESSAGE).replace(ReplaceTexts.PLAYER_NAME,event.player.name)))
            // 통장 발급
            BalanceManager.createPlayerBalance(event.player)
            // 튜토리얼 이동
            if(defaultConfig.getRawData(ConfigNames.TUTORIAL_LOCATION) != null){
                Bukkit.getScheduler().runTaskLater(instance, Runnable {
                    event.player.teleport(LocationManager.getLocation(gson.fromJson(defaultConfig.getJsonObject(ConfigNames.TUTORIAL_LOCATION), LocationData::class.java)!!))
                },60L)
            }

        }

    }

    @EventHandler
    private fun playerExit(event:PlayerQuitEvent){
        sendLeaveMessage(event)
        ScoreboardManager.removePlayerScoreboard(player = event.player)
    }

//    "플레이어 접속",
//    description = "${event.player.name}님",
//    color = Color(113, 179, 95),
//    timeStamp = ZonedDateTime.now(),
//    url = null,
//    image = null,

    /**
     *  입장 메시지 출력 함수
     */
    private fun sendJoinMessage(event:PlayerJoinEvent){
        event.joinMessage(mm(defaultConfig.getString(ConfigNames.PLAYER_JOIN).replace(ReplaceTexts.PLAYER_NAME,event.player.name)))
        Bukkit.getScheduler().runTaskAsynchronously(instance, Runnable {
            inGameWebhook
                .setEmbed(DiscordEmbedBuilder()
                    .setTitle("플레이어 접속")
                    .setDescription(event.player.name)
                    .setTimeStamp(ZonedDateTime.now(ZoneId.of("Asia/Seoul")))
                    .setColor(Color(113, 179, 95)).build()
                )
            inGameWebhook.execute()
        })
    }

    /**
     *  퇴장 메시지 출력 함수
     */
    private fun sendLeaveMessage(event:PlayerQuitEvent){
        event.quitMessage(mm(defaultConfig.getString(ConfigNames.PLAYER_EXIT).replace(ReplaceTexts.PLAYER_NAME,event.player.name)))
        Bukkit.getScheduler().runTaskAsynchronously(instance, Runnable {
            inGameWebhook
                .setEmbed(
                    DiscordEmbedBuilder()
                        .setTitle("플레이어 퇴장")
                        .setDescription(event.player.name)
                        .setTimeStamp(ZonedDateTime.now(ZoneId.of("Asia/Seoul")))
                        .setColor(Color(255, 79, 79)).build()
                )
            inGameWebhook.execute()
        })
    }

}