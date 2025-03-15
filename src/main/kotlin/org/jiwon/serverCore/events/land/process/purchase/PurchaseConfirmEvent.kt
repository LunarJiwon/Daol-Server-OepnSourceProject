package org.jiwon.serverCore.events.land.process.purchase

import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.TextComponent
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitScheduler
import org.jiwon.serverCore.ServerCore
import org.jiwon.serverCore.alternative.ReplaceTexts.LAND_NAME
import org.jiwon.serverCore.events.DefaultTaskEvent
import org.jiwon.serverCore.manager.BalanceManager
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.EventManager.unregisterEvent
import org.jiwon.serverCore.manager.LandManager
import org.jiwon.serverCore.manager.LocationManager
import org.jiwon.serverCore.messages.LandTexts.CREATED_LAND
import org.jiwon.serverCore.utils.AreaData
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.LandData

class PurchaseConfirmEvent(private val player:Player, private val landData: LandData,private val price:Int):Listener {

    private val defaultTask = DefaultTaskEvent(player,this,true)

    init{
        registerEvent(defaultTask)
    }

    @EventHandler(priority = EventPriority.LOW)
    private fun confirmEvent(event:AsyncChatEvent){
        if(event.player == player){
            event.isCancelled = true
            if((event.message() as TextComponent).content() == "확인"){
                LandManager.createPlayerLand(player, landData)
                BalanceManager.removePlayerBalance(player,price)
                player.sendMessage(mm(CREATED_LAND.replace(LAND_NAME,landData.landName)))
                Bukkit.getScheduler().runTask(ServerCore.instance, Runnable {
                    LocationManager.getLocation(landData.area.firstLocation).add(0.0,1.0,0.0).block.type = Material.GLASS
                    LocationManager.getLocation(landData.area.secondLocation).add(0.0,1.0,0.0).block.type = Material.GLASS
                })
                unregisterEvent(defaultTask)
                unregisterEvent(this)
            }

        }
    }





}