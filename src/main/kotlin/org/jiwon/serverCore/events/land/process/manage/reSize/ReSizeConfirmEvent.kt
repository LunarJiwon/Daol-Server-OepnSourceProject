package org.jiwon.serverCore.events.land.process.manage.reSize

import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.TextComponent
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.jiwon.serverCore.ServerCore
import org.jiwon.serverCore.alternative.ReplaceTexts.LAND_NAME
import org.jiwon.serverCore.events.DefaultTaskEvent
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.EventManager.unregisterEvent
import org.jiwon.serverCore.manager.LandManager
import org.jiwon.serverCore.manager.LocationManager
import org.jiwon.serverCore.messages.LandTexts.RE_SIZE_LAND
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.LandData

class ReSizeConfirmEvent(private val player: Player,private val landData: LandData):Listener {

    private val defaultTaskEvent = DefaultTaskEvent(player, this,true)

    init{
        registerEvent(defaultTaskEvent)
    }

    @EventHandler
    private fun confirmMessageEvent(event:AsyncChatEvent){
        if(event.player == player){
            event.isCancelled = true
            val message = (event.message() as TextComponent).content()
            if(message == "확인"){
                Bukkit.getScheduler().runTask(ServerCore.instance, Runnable {
                    LocationManager.getLocation(landData.area.firstLocation).add(0.0,1.0,0.0).block.type = Material.GLASS
                    LocationManager.getLocation(landData.area.secondLocation).add(0.0,1.0,0.0).block.type = Material.GLASS
                })

                player.sendMessage(mm(RE_SIZE_LAND.replace(LAND_NAME,landData.landName)))
                LandManager.setPlayerLand(player,landData)
                unregisterEvent(defaultTaskEvent)
                unregisterEvent(this)
            }

        }
    }


}