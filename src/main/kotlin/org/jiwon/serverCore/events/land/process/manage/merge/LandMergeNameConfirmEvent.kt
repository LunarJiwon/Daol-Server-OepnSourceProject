package org.jiwon.serverCore.events.land.process.manage.merge

import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.TextComponent
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
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
import org.jiwon.serverCore.messages.LandTexts.ALREADY_USING_LAND_NAME
import org.jiwon.serverCore.messages.LandTexts.LAND_MERGE_SUCCESS
import org.jiwon.serverCore.utils.AreaData
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.LandData
import org.jiwon.serverCore.utils.NumberUtils

class LandMergeNameConfirmEvent(private val player:Player, private val areaData: AreaData, private val remainBlock:Int, private val firstLandName:String, private val secondLandName:String):Listener {

    private val defaultTaskEvent = DefaultTaskEvent(player,this,false)

    init{
        registerEvent(defaultTaskEvent)
    }

    @EventHandler
    private fun mergeLandNameInputEvent(event:AsyncChatEvent){
        if(event.player == player){
            event.isCancelled = true
            val message = (event.message() as TextComponent).content()
            if(message != "취소"){
                if(LandManager.getPlayerLand(player,message) != null){
                    player.sendMessage(mm(ALREADY_USING_LAND_NAME))
                    return
                }
                LandManager.removePlayerLandFromLandName(player,firstLandName)
                LandManager.removePlayerLandFromLandName(player,secondLandName)
                LandManager.createPlayerLand(player,
                    LandData(
                        landName = message,
                        area = areaData,
                        remainArea = remainBlock,
                        settings = hashMapOf(
                            Pair(WELCOME_MESSAGE, ""),
                            Pair(EXIT_MESSAGE, ""),
                            Pair(LandSettings.ALLOW_EXPLODE,"false")
                        ), ArrayList(), ArrayList()
                    )
                )
                player.sendMessage(mm(LAND_MERGE_SUCCESS
                    .replace(LAND_NAME,message).replace(BLOCK_COUNT ,NumberUtils.format(remainBlock))))
                unregisterEvent(defaultTaskEvent)
                unregisterEvent(this)
            }
        }
    }


}