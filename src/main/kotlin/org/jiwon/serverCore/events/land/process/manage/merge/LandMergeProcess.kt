package org.jiwon.serverCore.events.land.process.manage.merge

import org.bukkit.Sound
import org.bukkit.entity.Player
import org.jiwon.serverCore.ServerCore.Companion.floodgateInstance
import org.jiwon.serverCore.alternative.ReplaceTexts.BLOCK_COUNT
import org.jiwon.serverCore.alternative.ReplaceTexts.LAND_NAME
import org.jiwon.serverCore.manager.AreaManager
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.LandManager
import org.jiwon.serverCore.manager.PlayerManager
import org.jiwon.serverCore.messages.LandTexts.LAND_MERGE_START
import org.jiwon.serverCore.messages.LandTexts.LIMITED_LAND_MERGE_SIZE
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.NumberUtils
import org.jiwon.serverCore.utils.PlatformTaskProcess

class LandMergeProcess(private val player:Player, private val landName:String): PlatformTaskProcess {

    init {
        platformCheck(player)
    }

    override fun bedrockRun() {
        floodgateInstance.getPlayer(player.uniqueId).sendForm(LandManager.bedrockLandListMenu(player,landName){
            val selectLandName = LandManager.getLandNameFromBedrockInterface(it.clickedButton().text())
            val firstLand = LandManager.getPlayerLand(player, landName)!!
            val secondLand = LandManager.getPlayerLand(player, selectLandName)!!
            val blockCount = (AreaManager.getBlockCountFromArea(firstLand.area) + firstLand.remainArea) +
                    (AreaManager.getBlockCountFromArea(secondLand.area) + secondLand.remainArea)
            if(blockCount > 10000){
                player.sendMessage(mm(LIMITED_LAND_MERGE_SIZE))
                PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
            }else{
                player.sendMessage(
                    mm(
                        LAND_MERGE_START.replace(LAND_NAME,selectLandName).replace(
                            BLOCK_COUNT,
                            NumberUtils.format(blockCount)))
                )
                LandMergeSelectEvent(player,blockCount,landName, selectLandName)
            }
        })
    }

    override fun javaRun() {
        player.openInventory(LandManager.landListMenu(player,landName))
        registerEvent(TargetLandSelectEvent(player,landName))
    }

}