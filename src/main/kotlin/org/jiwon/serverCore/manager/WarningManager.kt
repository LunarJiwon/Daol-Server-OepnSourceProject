package org.jiwon.serverCore.manager

import io.github.lunarjiwon.DiscordEmbed
import io.github.lunarjiwon.DiscordEmbedBuilder
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.jiwon.serverCore.ServerCore.Companion.instance
import org.jiwon.serverCore.ServerCore.Companion.warningData
import org.jiwon.serverCore.discord.DiscordWebhookManager.Companion.warningWebhook
import org.jiwon.serverCore.utils.NumberUtils
import java.awt.Color
import java.util.function.Consumer

object WarningManager {

    /**
     * @description 대상 플레이어에게 경고 부여 함수
     * @param player 경고 담당자
     * @param targetPlayer 대상 플레이어
     * @param reason 경고 부여 사유
     */
    fun addPlayerWarning(player:Player,targetPlayer: Player, reason:String){
        var count = 0
        if(warningData.getRawData(targetPlayer.uniqueId.toString()) != null) count = warningData.getInt(targetPlayer.uniqueId.toString())
        count++
        warningData.set(targetPlayer.uniqueId.toString(),count)
        warningData.save()
        Bukkit.getScheduler().runTaskAsynchronously(instance, Consumer {
            warningWebhook.setEmbed(
                DiscordEmbedBuilder().setTitle("경고 부여").addField(DiscordEmbed.Field("플레이어", targetPlayer.name, false))
                    .addField(DiscordEmbed.Field("누적 경고 횟수", NumberUtils.format(count), false))
                    .addField(DiscordEmbed.Field("경고 사유", reason, false))
                    .addField(DiscordEmbed.Field("담당자", player.name, false))
                    .setColor(Color(245, 66, 66))
                    .build()
            )
            warningWebhook.execute()
        })
    }

    /**
     * @description 대상 플레이어의 경고 확인 함수
     * @param player 대상 플레이어
     * @return 대상 플레이어의 경고 횟수
     */
    fun getPlayerWarning(player: Player):Int{
        return if(warningData.getRawData(player.uniqueId.toString()) != null) warningData.getInt(player.uniqueId.toString()) else 0
    }

    /**
     * @description 대상 플레이어에게 경고를 회수하는 함수
     * @param targetPlayer 대상 플레이어
     * @return 회수 처리 완료 여부
     */
    fun removePlayerWarning(player:Player,targetPlayer: Player):Boolean{
        var count = 0
        if(warningData.getRawData(targetPlayer.uniqueId.toString()) != null) count = warningData.getInt(targetPlayer.uniqueId.toString())
        if(count == 0) return false
        count--
        warningData.set(targetPlayer.uniqueId.toString(),count)
        warningData.save()
        Bukkit.getScheduler().runTaskAsynchronously(instance, Consumer {
            warningWebhook.setEmbed(
                DiscordEmbedBuilder().setTitle("경고 회수").addField(DiscordEmbed.Field("플레이어",targetPlayer.name,false)).addField(DiscordEmbed.Field("누적 경고 횟수",NumberUtils.format(count),false))
                    .addField(DiscordEmbed.Field("담당자",player.name,false))
                    .setColor(Color(69, 181, 114))
                    .build()
            )
            warningWebhook.execute()
        })
        return true

    }

}