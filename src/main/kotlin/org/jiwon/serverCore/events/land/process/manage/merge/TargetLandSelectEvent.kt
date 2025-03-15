package org.jiwon.serverCore.events.land.process.manage.merge

import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.jiwon.serverCore.alternative.ReplaceTexts.BLOCK_COUNT
import org.jiwon.serverCore.alternative.ReplaceTexts.LAND_NAME
import org.jiwon.serverCore.events.DefaultInventoryEvent
import org.jiwon.serverCore.manager.AreaManager
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.InventoryManager
import org.jiwon.serverCore.manager.LandManager
import org.jiwon.serverCore.manager.PlayerManager
import org.jiwon.serverCore.messages.LandTexts.LAND_MERGE_START
import org.jiwon.serverCore.messages.LandTexts.LIMITED_LAND_MERGE_SIZE
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.NumberUtils
import org.jiwon.serverCore.utils.PersistentDataContainerManager

class TargetLandSelectEvent(private val player:Player,private val landName:String):Listener {

    private val defaultInventoryEvent = DefaultInventoryEvent(player,this)

    init{
        registerEvent(defaultInventoryEvent)
    }

    @EventHandler
    private fun selectTargetEvent(event:InventoryClickEvent){
        if(event.whoClicked == player){
            event.isCancelled = true
            if(event.clickedInventory?.holder is InventoryManager){
                val itemData = event.currentItem?.itemMeta?.let { PersistentDataContainerManager.getItemCustomData(it) }
                if(itemData != null){
                    val firstLand = LandManager.getPlayerLand(player, landName)!!
                    val secondLand = LandManager.getPlayerLand(player, itemData)!!
                    val blockCount = (AreaManager.getBlockCountFromArea(firstLand.area) + firstLand.remainArea) +
                            (AreaManager.getBlockCountFromArea(secondLand.area) + secondLand.remainArea)
                    if(blockCount > 10000){
                        player.sendMessage(mm(LIMITED_LAND_MERGE_SIZE))
                        PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                        return
                    }
                    player.closeInventory()
                    player.sendMessage(mm(LAND_MERGE_START.replace(LAND_NAME,itemData).replace(BLOCK_COUNT,NumberUtils.format(blockCount))))
                    LandMergeSelectEvent(player,blockCount,landName, itemData)
                }
            }
        }
    }

}