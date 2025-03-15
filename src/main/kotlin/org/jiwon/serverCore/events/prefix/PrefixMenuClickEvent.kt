package org.jiwon.serverCore.events.prefix

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.jiwon.serverCore.alternative.ReplaceTexts.MESSAGE
import org.jiwon.serverCore.events.DefaultInventoryEvent
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.InventoryManager
import org.jiwon.serverCore.manager.PrefixManager
import org.jiwon.serverCore.messages.PrefixMessages.SET_PREFIX
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.PersistentDataContainerManager

class PrefixMenuClickEvent(private val player:Player):Listener {

    private val defaultInventoryEvent = DefaultInventoryEvent(player,this)

    init {
        registerEvent(defaultInventoryEvent)
    }

    @EventHandler
    private fun prefixMenuClickEvent(event:InventoryClickEvent){
        if(event.whoClicked == player){
            event.isCancelled = true
            if(event.clickedInventory?.holder is InventoryManager){
                val item = PersistentDataContainerManager.getItemCustomData(event.currentItem?.itemMeta!!)?.split(":")
                if(item?.isNotEmpty() == true){
                    PrefixManager.setPrefix(player,item[1])
                    player.sendMessage(mm(SET_PREFIX.replace(MESSAGE,item[1])))
                    player.closeInventory()
                }
            }
        }
    }

}