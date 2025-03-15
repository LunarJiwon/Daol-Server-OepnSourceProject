package org.jiwon.serverCore.events.land.process.manage.sell

import org.bukkit.entity.Player
import org.jiwon.serverCore.alternative.ReplaceTexts.LAND_NAME
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.messages.LandTexts
import org.jiwon.serverCore.utils.Components.mm

class LandSellProcess(private val player:Player, private val landName:String) {

    init{
        player.sendMessage(mm(LandTexts.LAND_SELL_CONFIRM.replace(LAND_NAME,landName)))
        registerEvent(LandSellConfirmEvent(player,landName))
    }

}