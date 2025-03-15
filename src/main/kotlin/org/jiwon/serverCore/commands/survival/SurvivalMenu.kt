package org.jiwon.serverCore.commands.survival

import org.bukkit.Sound
import org.bukkit.entity.Player
import org.jiwon.serverCore.ServerCore.Companion.floodgateInstance
import org.jiwon.serverCore.events.survival.SurvivalBedrockMenuClickEvent
import org.jiwon.serverCore.events.survival.SurvivalMenuClickEvent
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.PlayerManager
import org.jiwon.serverCore.manager.SurvivalManager
import org.jiwon.serverCore.utils.PlatformTaskProcess

class SurvivalMenu(val player:Player):PlatformTaskProcess {

    init {
        platformCheck(player)
    }

    override fun bedrockRun() {
        val floodgatePlayer = floodgateInstance.getPlayer(player.uniqueId)
        floodgatePlayer.sendForm(SurvivalManager.survivalBedrockMenu(){
            SurvivalBedrockMenuClickEvent(
                player,
                when(it.clickedButtonId()){
                    0->SurvivalManager.SurvivalOptions.Survival
                    1->SurvivalManager.SurvivalOptions.Nether
                    else->SurvivalManager.SurvivalOptions.End
                }
            )
        })
    }

    override fun javaRun() {
        player.openInventory(SurvivalManager.survivalMenu())
        PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_HARP)
        registerEvent(SurvivalMenuClickEvent(player))
    }
}