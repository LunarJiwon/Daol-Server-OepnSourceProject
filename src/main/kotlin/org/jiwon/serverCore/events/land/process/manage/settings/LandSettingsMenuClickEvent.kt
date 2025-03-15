package org.jiwon.serverCore.events.land.process.manage.settings

import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.jiwon.serverCore.alternative.LandSettings
import org.jiwon.serverCore.events.DefaultInventoryEvent
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.LandManager
import org.jiwon.serverCore.manager.LocationManager
import org.jiwon.serverCore.manager.PlayerManager
import org.jiwon.serverCore.utils.PersistentDataContainerManager

class LandSettingsMenuClickEvent(private val player:Player,private val landName:String):Listener {

    private val defaultInventoryEvent = DefaultInventoryEvent(player,this)

    init{
        registerEvent(defaultInventoryEvent)
    }

    @EventHandler
    private fun settingsMenuClickEvent(event:InventoryClickEvent){
        if(event.whoClicked == player){
            event.isCancelled = true
            val menuName = event.currentItem?.itemMeta?.let { PersistentDataContainerManager.getItemCustomData(it) }
            if(menuName != null){
                player.closeInventory()
                PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_HARP)
                LandSetSettings(player,landName,menuName)
            }
        }
    }

}