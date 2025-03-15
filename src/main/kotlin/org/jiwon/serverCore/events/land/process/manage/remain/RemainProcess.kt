package org.jiwon.serverCore.events.land.process.manage.remain

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.jiwon.serverCore.alternative.ReplaceTexts.BLOCK_COUNT
import org.jiwon.serverCore.events.land.process.LandSelectTask
import org.jiwon.serverCore.manager.AreaManager
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.LandManager
import org.jiwon.serverCore.manager.LocationManager
import org.jiwon.serverCore.messages.LandTexts.CONFIRM_SELECT_BLOCK
import org.jiwon.serverCore.messages.LandTexts.OVERLAP_AREA
import org.jiwon.serverCore.messages.LandTexts.OVER_BLOCK_COUNT
import org.jiwon.serverCore.messages.LandTexts.REMAIN_START
import org.jiwon.serverCore.utils.AreaData
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.LandData
import org.jiwon.serverCore.utils.NumberUtils

class RemainProcess(private val player:Player,private val beforeLandData:LandData):LandSelectTask(player,null,null) {

    init{
        player.sendMessage(mm(REMAIN_START))
    }

    override fun result(areaData: AreaData) {
        if(LandManager.isOverlapArea(areaData).isNotEmpty()){
            player.sendMessage(mm(OVERLAP_AREA))
            try{
                player.sendBlockChange(LocationManager.getLocation(areaData.firstLocation).add(0.0,1.0,0.0), Bukkit.createBlockData(Material.AIR))
                player.sendBlockChange(LocationManager.getLocation(areaData.secondLocation).add(0.0,1.0,0.0), Bukkit.createBlockData(Material.AIR))
            }catch (_:Exception){}
            firstTask()
            return
        }
        val tempRemainBlock = AreaManager.getBlockCountFromArea(areaData)
        if(tempRemainBlock > beforeLandData.remainArea){
            player.sendMessage(mm(OVER_BLOCK_COUNT.replace(BLOCK_COUNT,NumberUtils.format(tempRemainBlock - beforeLandData.remainArea))))
            try{
                player.sendBlockChange(
                    LocationManager.getLocation(areaData.firstLocation).add(0.0,1.0,0.0), Bukkit.createBlockData(
                        Material.AIR))
                player.sendBlockChange(
                    LocationManager.getLocation(areaData.secondLocation).add(0.0,1.0,0.0), Bukkit.createBlockData(
                        Material.AIR))
            }catch (_:Exception){}
            firstTask()
            return
        }
        player.sendMessage(mm(CONFIRM_SELECT_BLOCK.replace(
            BLOCK_COUNT,NumberUtils.format(tempRemainBlock)
        )))
        registerEvent(RemainNameConfirmEvent(player,areaData,beforeLandData,beforeLandData.remainArea-tempRemainBlock))

    }
}