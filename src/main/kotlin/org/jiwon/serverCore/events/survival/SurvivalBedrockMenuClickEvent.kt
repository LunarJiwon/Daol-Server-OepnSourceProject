package org.jiwon.serverCore.events.survival

import org.bukkit.Sound
import org.bukkit.entity.Player
import org.jiwon.serverCore.manager.BalanceManager
import org.jiwon.serverCore.manager.PlayerManager
import org.jiwon.serverCore.manager.SurvivalManager
import org.jiwon.serverCore.messages.EconomyTexts.LACK_BALANCE
import org.jiwon.serverCore.messages.SurvivalMessage.TELEPORTED_WORLD_END
import org.jiwon.serverCore.messages.SurvivalMessage.TELEPORTED_WORLD_NETHER
import org.jiwon.serverCore.messages.SurvivalMessage.TELEPORTED_WORLD_SURVIVAL
import org.jiwon.serverCore.messages.SurvivalMessage.WORLD_READY
import org.jiwon.serverCore.utils.Components.mm

class SurvivalBedrockMenuClickEvent(player: Player,type:SurvivalManager.SurvivalOptions){
    init {
        val price = SurvivalManager.getTollPrice(type)
        if (BalanceManager.getPlayerBalance(player) >= price) {
            val location = SurvivalManager.getTeleportLocation(type)
            if (location != null) {
                player.teleport(location)
                BalanceManager.removePlayerBalance(player,price)
                PlayerManager.playerSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP)
                player.sendMessage(
                    mm(
                        when (type) {
                            SurvivalManager.SurvivalOptions.Survival -> TELEPORTED_WORLD_SURVIVAL
                            SurvivalManager.SurvivalOptions.Nether -> TELEPORTED_WORLD_NETHER
                            else -> TELEPORTED_WORLD_END
                        }
                    )
                )
            } else {
                PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                player.sendMessage(
                    mm(
                        WORLD_READY
                    )
                )
            }
        } else {
            PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
            player.sendMessage(
                mm(
                    LACK_BALANCE
                )
            )
        }
    }
}