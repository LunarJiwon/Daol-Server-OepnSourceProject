package org.jiwon.serverCore.events.land.process.manage.merge

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.jiwon.serverCore.alternative.ReplaceTexts.BLOCK_COUNT
import org.jiwon.serverCore.events.DefaultTaskEvent
import org.jiwon.serverCore.events.land.process.LandSelectTask
import org.jiwon.serverCore.manager.AreaManager
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.LandManager
import org.jiwon.serverCore.manager.LocationManager
import org.jiwon.serverCore.manager.PlayerManager
import org.jiwon.serverCore.messages.LandTexts.LAND_MERGE_NAME
import org.jiwon.serverCore.messages.LandTexts.OVERLAP_AREA
import org.jiwon.serverCore.messages.LandTexts.OVER_BLOCK_COUNT
import org.jiwon.serverCore.utils.AreaData
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.NumberUtils

class LandMergeSelectEvent(private val player:Player,private val blockCount:Int,private val firstLandName:String, private val secondLandName:String): LandSelectTask(player,firstLandName,secondLandName) {


    override fun result(areaData: AreaData) {
        val overlapLand = LandManager.isOverlapArea(areaData)
        var isOverlap = false
        overlapLand.forEach {
            if(it.key != player.uniqueId.toString()){
                isOverlap = true
            }else{
                if(it.value.landName != firstLandName && it.value.landName != secondLandName){
                    isOverlap = true
                }
            }
        }
        if(isOverlap){
            PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
            player.sendMessage(mm(OVERLAP_AREA))
            try{
                player.sendBlockChange(LocationManager.getLocation(areaData.firstLocation).add(0.0,1.0,0.0), Bukkit.createBlockData(Material.AIR))
                player.sendBlockChange(LocationManager.getLocation(areaData.secondLocation).add(0.0,1.0,0.0), Bukkit.createBlockData(Material.AIR))
            }catch (_:Exception){}
            return firstTask()
        }
        val tempBlockCount = AreaManager.getBlockCountFromArea(areaData)
        if(tempBlockCount > blockCount){
            player.sendMessage(mm(OVER_BLOCK_COUNT.replace(BLOCK_COUNT,NumberUtils.format(tempBlockCount-blockCount))))
            try{
                player.sendBlockChange(
                    LocationManager.getLocation(areaData.firstLocation).add(0.0,1.0,0.0), Bukkit.createBlockData(
                        Material.AIR))
                player.sendBlockChange(
                    LocationManager.getLocation(areaData.secondLocation).add(0.0,1.0,0.0), Bukkit.createBlockData(
                        Material.AIR))
            }catch (_:Exception){}
            return firstTask()
        }
        player.sendMessage(mm(LAND_MERGE_NAME.replace(BLOCK_COUNT,NumberUtils.format(blockCount-tempBlockCount))))
        registerEvent(LandMergeNameConfirmEvent(player,areaData,blockCount-tempBlockCount,firstLandName,secondLandName))


    }


}