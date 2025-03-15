package org.jiwon.serverCore.events.land.process.manage.owner

import org.bukkit.Sound
import org.bukkit.entity.Player
import org.jiwon.serverCore.ServerCore.Companion.defaultConfig
import org.jiwon.serverCore.alternative.ConfigNames.LAND_TAX
import org.jiwon.serverCore.alternative.ReplaceTexts.LAND_NAME
import org.jiwon.serverCore.manager.AreaManager
import org.jiwon.serverCore.manager.BalanceManager
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.LandManager
import org.jiwon.serverCore.manager.PlayerManager
import org.jiwon.serverCore.messages.EconomyTexts
import org.jiwon.serverCore.messages.LandTexts
import org.jiwon.serverCore.utils.Components.mm

class OwnerChangeProcess(private val player: Player, private val landName:String) {
    init{

        val land = LandManager.getPlayerLand(player, landName)!!
        val blockCount = AreaManager.getBlockCountFromArea(land.area) + land.remainArea
        val price = (blockCount * 100) + (blockCount * defaultConfig.getFloat(
            LAND_TAX
        )).toInt()
        if(BalanceManager.getPlayerBalance(player) >= price) {
            player.sendMessage(mm(LandTexts.LAND_OWNER_CHANGE_START.replace(LAND_NAME,landName)))
            registerEvent(OwnerSelectEvent(player, landName, price))
        }else{
            player.sendMessage(mm(EconomyTexts.LACK_BALANCE))
            PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
        }
    }
}