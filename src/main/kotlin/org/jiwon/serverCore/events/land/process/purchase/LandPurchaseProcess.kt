package org.jiwon.serverCore.events.land.process.purchase

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.jiwon.serverCore.alternative.ReplaceTexts.BLOCK_COUNT
import org.jiwon.serverCore.alternative.ReplaceTexts.TARGET_PLAYER_NAME
import org.jiwon.serverCore.events.land.process.LandSelectTask
import org.jiwon.serverCore.manager.AreaManager
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.LandManager
import org.jiwon.serverCore.manager.LocationManager
import org.jiwon.serverCore.manager.PlayerManager
import org.jiwon.serverCore.manager.PlayerManager.getPlayerName
import org.jiwon.serverCore.messages.LandTexts.CONFIRM_SELECT_BLOCK
import org.jiwon.serverCore.messages.LandTexts.OVERLAP_AREA
import org.jiwon.serverCore.messages.LandTexts.OVER_BLOCK_COUNT
import org.jiwon.serverCore.messages.LandTexts.SELECT_FIRST_LOCATION
import org.jiwon.serverCore.messages.LandTexts.START_LOCATION_SELECT
import org.jiwon.serverCore.utils.AreaData
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.NumberUtils

class LandPurchaseProcess(val player: Player, private val blockCount:Int, private val price:Int): LandSelectTask(player,null,null) {

    init{
        player.sendMessage(mm(START_LOCATION_SELECT))
        PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_HARP)
    }

    override fun result(areaData: AreaData) {
        val tempBlockCount = AreaManager.getBlockCountFromArea(areaData)
        if(blockCount < tempBlockCount){
            try{
                player.sendBlockChange(LocationManager.getLocation(areaData.firstLocation).add(0.0,1.0,0.0),Bukkit.createBlockData(Material.AIR))
                player.sendBlockChange(LocationManager.getLocation(areaData.secondLocation).add(0.0,1.0,0.0),Bukkit.createBlockData(Material.AIR))
            }catch (_:Exception){}
            player.sendMessage(mm(OVER_BLOCK_COUNT.replace(BLOCK_COUNT,NumberUtils.format(tempBlockCount-blockCount))))
            PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
            return firstTask()
        }else{
            val areaOwner = LandManager.isOverlapArea(areaData)
            if(areaOwner.isEmpty()) {
                player.sendMessage(
                    mm(
                        CONFIRM_SELECT_BLOCK
                            .replace(BLOCK_COUNT, NumberUtils.format(tempBlockCount))
                    )
                )
                registerEvent(PurchaseLandNameConfirmEvent(player, areaData, price, blockCount - tempBlockCount))
            }else{
                try{
                    player.sendBlockChange(LocationManager.getLocation(areaData.firstLocation).add(0.0,1.0,0.0),Bukkit.createBlockData(Material.AIR))
                    player.sendBlockChange(LocationManager.getLocation(areaData.secondLocation).add(0.0,1.0,0.0),Bukkit.createBlockData(Material.AIR))
                }catch (_:Exception){}
                player.sendMessage(mm(OVERLAP_AREA))
                PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                return firstTask()
            }
        }
    }

}