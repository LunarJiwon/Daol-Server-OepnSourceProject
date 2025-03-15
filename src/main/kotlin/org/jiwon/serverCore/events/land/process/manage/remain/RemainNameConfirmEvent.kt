package org.jiwon.serverCore.events.land.process.manage.remain

import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.TextComponent
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.jiwon.serverCore.ServerCore
import org.jiwon.serverCore.alternative.LandSettings
import org.jiwon.serverCore.alternative.LandSettings.EXIT_MESSAGE
import org.jiwon.serverCore.alternative.LandSettings.WELCOME_MESSAGE
import org.jiwon.serverCore.alternative.ReplaceTexts.BLOCK_COUNT
import org.jiwon.serverCore.alternative.ReplaceTexts.LAND_NAME
import org.jiwon.serverCore.events.DefaultTaskEvent
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.EventManager.unregisterEvent
import org.jiwon.serverCore.manager.LandManager
import org.jiwon.serverCore.manager.LocationManager
import org.jiwon.serverCore.messages.LandTexts.ALREADY_USING_LAND_NAME
import org.jiwon.serverCore.messages.LandTexts.REMAIN_CREATED
import org.jiwon.serverCore.utils.AreaData
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.LandData
import org.jiwon.serverCore.utils.NumberUtils

class RemainNameConfirmEvent(private val player:Player,private val areaData: AreaData,private val beforeLandData:LandData,private val remainBlock:Int):Listener {

    private val defaultTaskEvent = DefaultTaskEvent(player,this,false)

    init{
        registerEvent(defaultTaskEvent)
    }

    @EventHandler
    private fun nameConfirmEvent(event:AsyncChatEvent){
        if(event.player == player){
            val message = (event.message() as TextComponent).content()
            if(message != "취소"){
                if(LandManager.getPlayerLandFromLandName(player,message) != null){
                    player.sendMessage(mm(ALREADY_USING_LAND_NAME))
                    return
                }
                LandManager.createPlayerLand(player, LandData(
                    message,
                    areaData,
                    remainBlock,
                    hashMapOf(
                        Pair(WELCOME_MESSAGE, ""),
                        Pair(EXIT_MESSAGE, ""),
                        Pair(LandSettings.ALLOW_EXPLODE,"false")
                    ), ArrayList(), ArrayList()
                ))
                LandManager.removePlayerLandFromLandName(player,beforeLandData.landName)
                LandManager.createPlayerLand(player,beforeLandData.copy(
                    remainArea = 0
                ))
                Bukkit.getScheduler().runTask(ServerCore.instance, Runnable {
                    LocationManager.getLocation(areaData.firstLocation).add(0.0,1.0,0.0).block.type = Material.GLASS
                    LocationManager.getLocation(areaData.secondLocation).add(0.0,1.0,0.0).block.type = Material.GLASS
                })

                player.sendMessage(mm(REMAIN_CREATED.replace(LAND_NAME,message).replace(BLOCK_COUNT,NumberUtils.format(remainBlock))))
                unregisterEvent(defaultTaskEvent)
                unregisterEvent(this)
            }
        }
    }

}