package org.jiwon.serverCore.events.land.process.manage.sell

import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.TextComponent
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.jiwon.serverCore.alternative.ReplaceTexts.LAND_NAME
import org.jiwon.serverCore.alternative.ReplaceTexts.PRICE
import org.jiwon.serverCore.events.DefaultTaskEvent
import org.jiwon.serverCore.manager.AreaManager
import org.jiwon.serverCore.manager.BalanceManager
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.EventManager.unregisterEvent
import org.jiwon.serverCore.manager.LandManager
import org.jiwon.serverCore.messages.LandTexts.SUCCESS_LAND_SELL
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.NumberUtils

class LandSellConfirmEvent(private val player: Player,private val landName:String):Listener {

    private val defaultTaskEvent = DefaultTaskEvent(player,this,true)

    init{
        registerEvent(defaultTaskEvent)
    }

    @EventHandler
    private fun landSellConfirmEvent(event:AsyncChatEvent){
        if(event.player == player){
            event.isCancelled = true
            val message = (event.message() as TextComponent).content()
            if(message == "확인"){
                val land = LandManager.getPlayerLand(player, landName)
                val sellPrice = (AreaManager.getBlockCountFromArea(land!!.area) + land.remainArea) * 50
                LandManager.removePlayerLandFromLandName(player, landName)
                BalanceManager.addPlayerBalance(player,sellPrice)
                player.sendMessage(mm(SUCCESS_LAND_SELL.replace(LAND_NAME,landName).replace(PRICE,NumberUtils.format(sellPrice))))
                unregisterEvent(defaultTaskEvent)
                unregisterEvent(this)
            }
        }
    }

}