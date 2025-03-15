package org.jiwon.serverCore.events.prefix

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.jiwon.serverCore.alternative.ReplaceTexts.MESSAGE
import org.jiwon.serverCore.manager.PrefixManager
import org.jiwon.serverCore.messages.PrefixMessages.ALREADY_PREFIX
import org.jiwon.serverCore.messages.PrefixMessages.SET_PREFIX
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.PersistentDataContainerManager

class PrefixApplyEvent(event: PlayerInteractEvent) {

    init {
        if(event.action == Action.RIGHT_CLICK_AIR ||
            event.action == Action.RIGHT_CLICK_BLOCK ||
            event.action == Action.LEFT_CLICK_AIR ||
            event.action == Action.LEFT_CLICK_BLOCK){
            if(event.player.inventory.itemInMainHand.type == Material.NAME_TAG){
                val data = PersistentDataContainerManager.getItemCustomData(event.player.inventory.itemInMainHand.itemMeta)
                if(data != null){
                    if(!PrefixManager.addPrefixPlayer(event.player,data.split(":")[1])){
                        event.player.sendMessage(mm(ALREADY_PREFIX.replace(MESSAGE,data.split(":")[1])))
                        event.isCancelled = true
                    }else{
                        event.player.inventory.itemInMainHand.amount = 0
                        event.player.updateInventory()
//                        event.player.inventory.removeItem(event.player.inventory.itemInMainHand)
                        event.player.sendMessage(mm(SET_PREFIX.replace(MESSAGE,data.split(":")[1])))
                        event.isCancelled = true
                    }
                }

            }
        }
    }

}