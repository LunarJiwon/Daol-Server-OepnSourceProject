package org.jiwon.serverCore.manager

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.jiwon.serverCore.ServerCore.Companion.instance
import org.jiwon.serverCore.messages.AFKMessages.ADDED_POINT
import org.jiwon.serverCore.messages.AFKMessages.END_AFK
import org.jiwon.serverCore.messages.AFKMessages.STARTED_AFK
import org.jiwon.serverCore.messages.MessageColors.BOLD
import org.jiwon.serverCore.messages.MessageColors.LIGHT_YELLOW
import org.jiwon.serverCore.messages.MessageColors.PURPLE
import org.jiwon.serverCore.messages.MessageColors.RESET
import org.jiwon.serverCore.messages.MessageColors.WHITE
import org.jiwon.serverCore.utils.Components.mm

object AFKManager {
    private val afkPlayers = HashMap<Player,Int>()

    private fun createAFKCoinItem():ItemStack{
        return ItemManager.createItem(Material.AMETHYST_SHARD,mm("${WHITE}${BOLD}[ ${RESET}${PURPLE}잠수코인 ${WHITE}${BOLD}]"),
            mutableListOf(
                mm("${LIGHT_YELLOW}잠수장에서 잠수를 통해 획득할 수 있습니다.")
            ),
            true,
            "AFKCOIN"
        )
    }

    fun startPlayerAFK(player: Player){
        player.sendMessage(mm(STARTED_AFK))
        afkPlayers[player] = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance,{
            if(Bukkit.getOnlinePlayers().any { it == player }){
                player.inventory.addItem(createAFKCoinItem())
                player.sendMessage(mm(ADDED_POINT))
            }else{
                Bukkit.getScheduler().cancelTask(afkPlayers[player]!!)
            }


        },1200,1200)
    }

    fun stopPlayerAFK(player: Player){
        player.sendMessage(mm(END_AFK))
        if(afkPlayers[player] != null){
            Bukkit.getScheduler().cancelTask(afkPlayers[player]!!)
        }
    }



}