package org.jiwon.serverCore.events.land.process.manage.buildAllow

import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.jiwon.serverCore.alternative.ReplaceTexts.LAND_NAME
import org.jiwon.serverCore.alternative.ReplaceTexts.PLAYER_NAME
import org.jiwon.serverCore.events.DefaultInventoryEvent
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.InventoryManager
import org.jiwon.serverCore.manager.LandManager
import org.jiwon.serverCore.manager.PlayerManager
import org.jiwon.serverCore.manager.PlayerManager.getPlayerName
import org.jiwon.serverCore.messages.LandTexts
import org.jiwon.serverCore.messages.LandTexts.BUILD_ALLOW_PLAYER_ADD_START
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.PersistentDataContainerManager

class BuildAllowPlayerClickEvent(private val player:Player,private val landName:String):Listener {

    private val defaultInventoryEvent = DefaultInventoryEvent(player,this)

    init{
        registerEvent(defaultInventoryEvent)
    }

    @EventHandler
    private fun playerClickEvent(event:InventoryClickEvent){
        if(event.whoClicked == player){
            event.isCancelled = true
            if(event.clickedInventory?.holder is InventoryManager){
                val currentPlayer = PersistentDataContainerManager.getItemCustomData(event.currentItem?.itemMeta!!)
                PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_HARP)
                if(currentPlayer != null){
                    LandManager.removeBuildAllowPlayer(player,landName,currentPlayer)
                    player.sendMessage(mm(LandTexts.REMOVED_BUILD_ALLOW_PLAYER.replace(LAND_NAME,landName).replace(
                        PLAYER_NAME,getPlayerName(currentPlayer)!!)))
                    player.closeInventory()
                }else{
                    player.sendMessage(mm(BUILD_ALLOW_PLAYER_ADD_START))
                    player.closeInventory()
                    PlayerManager.playerSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP)
                    registerEvent(BuildAllowPlayerSelectEvent(player,landName))
                }
            }
        }
    }

}