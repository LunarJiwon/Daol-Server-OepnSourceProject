package org.jiwon.serverCore.events.land.process.manage.reSize

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.jiwon.serverCore.alternative.ReplaceTexts.BLOCK_COUNT
import org.jiwon.serverCore.events.land.process.LandSelectTask
import org.jiwon.serverCore.manager.AreaManager
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.LandManager
import org.jiwon.serverCore.manager.LocationManager
import org.jiwon.serverCore.messages.LandTexts.OVERLAP_AREA
import org.jiwon.serverCore.messages.LandTexts.OVER_BLOCK_COUNT
import org.jiwon.serverCore.messages.LandTexts.RE_SIZE_LAND_CONFIRM
import org.jiwon.serverCore.messages.LandTexts.RE_SIZE_LAND_START
import org.jiwon.serverCore.utils.AreaData
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.NumberUtils

class ReSelectLandProcess(private val player:Player,private val landName:String): LandSelectTask(player,landName,null) {

    init{
        player.sendMessage(mm(RE_SIZE_LAND_START))
    }

    override fun result(areaData: AreaData) {
        val playerLand = LandManager.getPlayerLand(player, landName)!!
        val maxBlockCount = AreaManager.getBlockCountFromArea(playerLand.area) + playerLand.remainArea
        val tempBlockCount = AreaManager.getBlockCountFromArea(areaData)
        if(tempBlockCount > maxBlockCount){
            player.sendMessage(mm(OVER_BLOCK_COUNT.replace(BLOCK_COUNT,NumberUtils.format(tempBlockCount-maxBlockCount))))
            return firstTask()
        }
        val overlapLand = LandManager.isOverlapArea(areaData)
        if(overlapLand.isNotEmpty()){
            if(overlapLand.filterNot { it.key == player.uniqueId.toString() && it.value.landName == landName }.isNotEmpty()){
                try{
                    player.sendBlockChange(LocationManager.getLocation(areaData.firstLocation).add(0.0,1.0,0.0), Bukkit.createBlockData(Material.AIR))
                    player.sendBlockChange(LocationManager.getLocation(areaData.secondLocation).add(0.0,1.0,0.0), Bukkit.createBlockData(Material.AIR))
                }catch (_:Exception){}
                player.sendMessage(mm(OVERLAP_AREA))
                return firstTask()
            }
        }

        player.sendMessage(mm(RE_SIZE_LAND_CONFIRM.replace(BLOCK_COUNT,NumberUtils.format(maxBlockCount - tempBlockCount))))
        registerEvent(
            ReSizeConfirmEvent(player,playerLand.copy(
            area = areaData
        ))
        )



    }




}