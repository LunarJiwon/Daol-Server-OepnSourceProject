package org.jiwon.serverCore.events.land.process.purchase

import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.TextComponent
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.jiwon.serverCore.alternative.LandSettings
import org.jiwon.serverCore.alternative.LandSettings.EXIT_MESSAGE
import org.jiwon.serverCore.alternative.LandSettings.WELCOME_MESSAGE
import org.jiwon.serverCore.alternative.ReplaceTexts.BLOCK_COUNT
import org.jiwon.serverCore.alternative.ReplaceTexts.LAND_NAME
import org.jiwon.serverCore.events.DefaultTaskEvent
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.EventManager.unregisterEvent
import org.jiwon.serverCore.manager.LandManager
import org.jiwon.serverCore.messages.LandTexts.CONFIRM_LAND
import org.jiwon.serverCore.messages.LandTexts.ALREADY_USING_LAND_NAME
import org.jiwon.serverCore.utils.AreaData
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.LandData
import org.jiwon.serverCore.utils.NumberUtils

class PurchaseLandNameConfirmEvent(private val player:Player, private val areaData: AreaData, private val price:Int,private val remainBlock:Int):Listener {

    private val defaultTask = DefaultTaskEvent(player,this,false)

    init{
        registerEvent(defaultTask)
    }

    @EventHandler(priority = EventPriority.LOW)
    private fun setNameEvent(event: AsyncChatEvent){
        if(event.player == player){
            val message = (event.message() as TextComponent).content()
            if(message != "취소") {
                event.isCancelled = true
                LandManager.getPlayerLandList(player)?.forEach {
                    if (it.landName == message) {
                        player.sendMessage(mm(ALREADY_USING_LAND_NAME))
                        return
                    }
                }
                val landData = LandData(
                    message, areaData, remainBlock, hashMapOf(
                        Pair(WELCOME_MESSAGE, ""),
                        Pair(EXIT_MESSAGE, ""),
                        Pair(LandSettings.ALLOW_EXPLODE,"false")
                    ), ArrayList(), ArrayList()
                )
                player.sendMessage(
                    mm(
                        CONFIRM_LAND.replace(BLOCK_COUNT, NumberUtils.format(remainBlock))
                            .replace(LAND_NAME, landData.landName)
                    )
                )
                registerEvent(PurchaseConfirmEvent(player, landData, price))
                unregisterEvent(defaultTask)
                unregisterEvent(this)
            }
        }
    }

}