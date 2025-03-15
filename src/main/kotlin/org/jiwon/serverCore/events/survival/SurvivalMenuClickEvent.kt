package org.jiwon.serverCore.events.survival

import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.jiwon.serverCore.events.DefaultInventoryEvent
import org.jiwon.serverCore.manager.BalanceManager
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.InventoryManager
import org.jiwon.serverCore.manager.PlayerManager
import org.jiwon.serverCore.manager.SurvivalManager
import org.jiwon.serverCore.messages.EconomyTexts.LACK_BALANCE
import org.jiwon.serverCore.messages.SurvivalMessage.TELEPORTED_WORLD_END
import org.jiwon.serverCore.messages.SurvivalMessage.TELEPORTED_WORLD_NETHER
import org.jiwon.serverCore.messages.SurvivalMessage.TELEPORTED_WORLD_SURVIVAL
import org.jiwon.serverCore.messages.SurvivalMessage.WORLD_READY
import org.jiwon.serverCore.utils.Components.mm

class SurvivalMenuClickEvent(private val player: Player):Listener {

    private val defaultInventoryTask = DefaultInventoryEvent(player,this)

    init{
        registerEvent(defaultInventoryTask)
    }

    @EventHandler
    private fun menuClickEvent(event:InventoryClickEvent){
        if(event.whoClicked == player){
            event.isCancelled = true
            if(event.clickedInventory?.holder is InventoryManager) {
                val type = when(event.slot) {
                    2 -> SurvivalManager.SurvivalOptions.Survival
                    4 -> SurvivalManager.SurvivalOptions.Nether
                    6 -> SurvivalManager.SurvivalOptions.End
                    else -> null
                }
                if(type != null){
                    val tollPrice = SurvivalManager.getTollPrice(type)
                    if(BalanceManager.getPlayerBalance(player) >= tollPrice){
                        val location = SurvivalManager.getTeleportLocation(type)
                        if(location != null) {
                            player.teleport(location)
                            BalanceManager.removePlayerBalance(player,tollPrice)
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
                            player.closeInventory()
                        }else{
                            PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                            player.sendMessage(
                                mm(
                                    WORLD_READY
                                )
                            )
                        }
                    }else{
                        PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                        player.sendMessage(mm(
                            LACK_BALANCE
                        ))
                    }


                }
            }
        }
    }

}