package org.jiwon.serverCore.events.economy

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.jiwon.serverCore.ServerCore.Companion.instance
import org.jiwon.serverCore.events.DefaultTaskEvent
import org.jiwon.serverCore.manager.EconomyManager
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.EventManager.unregisterEvent
import org.jiwon.serverCore.utils.EconomyData
import org.jiwon.serverCore.messages.SystemMessages.REQUIRE_SIGN
import org.jiwon.serverCore.messages.SystemMessages.SUCCESS_CREATE_STORE
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.manager.LocationManager
import org.jiwon.serverCore.manager.PlayerManager
import org.jiwon.serverCore.messages.EconomyTexts.ALREADY_SHOP

class CreateEconomyEvent(val player:Player, private val economyData: EconomyData, val callBack:(EconomyData)->Unit):Listener {

    private val defaultTaskEvent = DefaultTaskEvent(player,this,true)

    init{
        registerEvent(defaultTaskEvent)
    }

    @EventHandler
    private fun signClickEvent(event:PlayerInteractEvent){
        if(event.player == player){
            event.isCancelled = true
            if(event.clickedBlock != null){
                if(event.clickedBlock!!.type == Material.OAK_SIGN || event.clickedBlock!!.type == Material.OAK_WALL_SIGN){
                    if(event.clickedBlock?.location?.let { EconomyManager.getStoreInSign(it) } != null){
                        player.sendMessage(mm(ALREADY_SHOP))
                        PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                        return
                    }
                    economyData.sign = LocationManager.createJsonLocation(event.clickedBlock!!.location)
                    callBack(economyData)
                    player.sendMessage(mm(SUCCESS_CREATE_STORE))
                    unregisterEvent(defaultTaskEvent)
                    unregisterEvent(this)
                    return
                }

            }
            player.sendMessage(mm(REQUIRE_SIGN))
        }
    }



}