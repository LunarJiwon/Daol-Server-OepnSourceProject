package org.jiwon.serverCore.commands.prefix

import org.bukkit.entity.Player
import org.jiwon.serverCore.ServerCore.Companion.floodgateInstance
import org.jiwon.serverCore.events.prefix.PrefixMenuClickBedrock
import org.jiwon.serverCore.events.prefix.PrefixMenuClickEvent
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.PrefixManager
import org.jiwon.serverCore.utils.PlatformTaskProcess

class PrefixMenu(private val player:Player):PlatformTaskProcess {

    init{
        platformCheck(player)
    }

    override fun bedrockRun() {
        floodgateInstance.getPlayer(player.uniqueId).sendForm(
            PrefixManager.prefixBedrockMenu(player){
                PrefixMenuClickBedrock.menuClick(player,it.clickedButton().text())
            }
        )
    }

    override fun javaRun() {
        player.openInventory(PrefixManager.prefixMenu(player))
        registerEvent(PrefixMenuClickEvent(player))
    }
}