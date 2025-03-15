package org.jiwon.serverCore.scoreboard

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.megavex.scoreboardlibrary.api.ScoreboardLibrary
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar
import net.megavex.scoreboardlibrary.api.sidebar.component.ComponentSidebarLayout
import net.megavex.scoreboardlibrary.api.sidebar.component.SidebarComponent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.jiwon.serverCore.ServerCore
import org.jiwon.serverCore.ServerCore.Companion.instance
import org.jiwon.serverCore.manager.AFKManager
import org.jiwon.serverCore.manager.BalanceManager
import org.jiwon.serverCore.messages.MessageColors.BOLD
import org.jiwon.serverCore.messages.MessageColors.GOLD
import org.jiwon.serverCore.messages.MessageColors.PURPLE
import org.jiwon.serverCore.messages.MessageColors.WHITE_GREEN
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.NumberUtils
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.UUID
import java.util.function.Supplier


class ScoreboardManager {
    companion object {
        val scoreboardLibrary = ScoreboardLibrary.loadScoreboardLibrary(instance)
        val sidebars = HashMap<UUID,Sidebar>()
        // 메모리 해제
        fun removePlayerScoreboard(player: Player){
            sidebars[player.uniqueId]!!.close()
            sidebars.remove(player.uniqueId)
        }
    }


    init{
        Bukkit.getScheduler().scheduleSyncRepeatingTask(instance,{
            Bukkit.getOnlinePlayers().forEach {
                if(sidebars[it.uniqueId] == null) {
                    sidebars[it.uniqueId] = scoreboardLibrary.createSidebar()
                    sidebars[it.uniqueId]!!.addPlayer(it)
                }
                updateScoreboard(it).apply(sidebars[it.uniqueId]!!)
            }
        },20,20)
    }



    private fun updateScoreboard(player: Player): ComponentSidebarLayout {
        val title = SidebarComponent.staticLine(mm("      ${PURPLE}${BOLD}다올서버      "))
        val lines = SidebarComponent.builder()
            .addBlankLine()
            .addStaticLine(mm("  <rainbow>다올서버에 오신것을 환영합니다!  "))
            .addBlankLine()
            .addStaticLine(mm("${GOLD}▶ 동접자 수"))
            .addDynamicLine {
                mm("${Bukkit.getOnlinePlayers().size}/${Bukkit.getMaxPlayers()}명")
            }
            .addBlankLine()
            .addStaticLine(mm("${GOLD}▶ 통장 잔고"))
            .addDynamicLine {
                mm("${NumberUtils.format(BalanceManager.getPlayerBalance(player))}원")
            }
            .addBlankLine()
            .addStaticLine(mm("${GOLD}▶ 현재 날짜"))
            .addDynamicLine {
                mm(LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")))
            }
            .addBlankLine()
            .build()
        val layout = ComponentSidebarLayout(title,lines)
        return layout

    }


}