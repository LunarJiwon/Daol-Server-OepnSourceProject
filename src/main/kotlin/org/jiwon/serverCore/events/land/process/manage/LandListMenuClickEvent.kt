package org.jiwon.serverCore.events.land.process.manage

import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.jiwon.serverCore.events.DefaultInventoryEvent
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.EventManager.unregisterEvent
import org.jiwon.serverCore.manager.InventoryManager
import org.jiwon.serverCore.manager.LandManager
import org.jiwon.serverCore.manager.PlayerManager
import org.jiwon.serverCore.utils.PersistentDataContainerManager

class LandListMenuClickEvent(private val player:Player):Listener {

    private val defaultInventoryEvent = DefaultInventoryEvent(player,this)

    init{
        registerEvent(defaultInventoryEvent)
    }

    @EventHandler
    private fun landClickEvent(event:InventoryClickEvent){
        if(event.whoClicked == player){
            event.isCancelled = true
            if(event.clickedInventory?.holder is InventoryManager){
                if(event.currentItem?.itemMeta?.let { PersistentDataContainerManager.getItemCustomData(it) } != null){
                    LandManager.getPlayerLandList(player).let {
                        player.closeInventory()
                        val landName = PersistentDataContainerManager.getItemCustomData(event.currentItem!!.itemMeta!!)
                        PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_HARP)
                        registerEvent(LandManageMenuClickEvent(player,landName!!))
                        player.openInventory(LandManager.landManageMenu(it?.find { land -> land.landName == landName }!!))
                    }
                }
            }
        }
    }
}