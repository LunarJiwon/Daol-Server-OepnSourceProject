package org.jiwon.serverCore.events.land

import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.jiwon.serverCore.events.DefaultInventoryEvent
import org.jiwon.serverCore.events.land.process.manage.LandListMenuClickEvent
import org.jiwon.serverCore.events.land.process.purchase.LandPurchaseMenuClickEvent
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.EventManager.unregisterEvent
import org.jiwon.serverCore.manager.InventoryManager
import org.jiwon.serverCore.manager.LandManager
import org.jiwon.serverCore.manager.PlayerManager
import org.jiwon.serverCore.messages.LandTexts.NOT_FOUND_LAND
import org.jiwon.serverCore.utils.Components.mm

class LandMainMenuClickEvent(val player:Player):Listener {

    private val defaultInventoryEvent = DefaultInventoryEvent(player,this)

    init {
        registerEvent(defaultInventoryEvent)
    }

    @EventHandler
    private fun menuClickEvent(event:InventoryClickEvent){
        if((event.whoClicked as Player) == player){
            event.isCancelled = true
            if(event.clickedInventory?.holder is InventoryManager){
                if(event.slot == 3 || event.slot == 5) {
                    if (event.slot == 3) { // 부동산 구매
                        player.openInventory(LandManager.landPurchaseMenu())
                        registerEvent(LandPurchaseMenuClickEvent(player))

                    } else if (event.slot == 5) { // 부동산 관리
                        if(LandManager.getPlayerLandSize(player) > 0){
                            player.openInventory(LandManager.landListMenu(player,null))
                            registerEvent(LandListMenuClickEvent(player))
                        }else{
                            player.sendMessage(mm(NOT_FOUND_LAND))
                            PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                            return
                        }
                    }
                    PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_HARP)
                    unregisterEvent(defaultInventoryEvent)
                    unregisterEvent(this)
                }
            }
        }
    }


}