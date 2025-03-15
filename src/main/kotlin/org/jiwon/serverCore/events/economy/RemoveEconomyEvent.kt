package org.jiwon.serverCore.events.economy

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

import org.jiwon.serverCore.ServerCore.Companion.instance
import org.jiwon.serverCore.events.DefaultTaskEvent
import org.jiwon.serverCore.manager.EconomyManager
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.EventManager.unregisterEvent
import org.jiwon.serverCore.messages.SystemMessages.NOT_FOUND_STORE
import org.jiwon.serverCore.messages.SystemMessages.REMOVE_STORE
import org.jiwon.serverCore.messages.SystemMessages.REQUEST_TO_CANCEL
import org.jiwon.serverCore.utils.Components.mm

class RemoveEconomyEvent(val player:Player) :Listener {
    private val defaultTaskEvent = DefaultTaskEvent(player,this,true)
    init{
        registerEvent(defaultTaskEvent)
        PlayerStoreSignClickEvent.ignorePlayer.add(player)
    }

    @EventHandler(priority = EventPriority.HIGH)
    private fun signBreakEvent(event:BlockBreakEvent){
        if(event.player == player){
            event.isCancelled = true
            if(event.block.type == Material.OAK_WALL_SIGN){
                val signData = EconomyManager.getStoreInSign(event.block.location)
                if(signData != null){
                    if(EconomyManager.removeStore(signData)){
                        event.isCancelled = false
                        event.player.sendMessage(mm(REMOVE_STORE))
                        PlayerStoreSignClickEvent.ignorePlayer.remove(player)
                        unregisterEvent(defaultTaskEvent)
                        unregisterEvent(this)
                        return
                    }

                }
                event.player.sendMessage(mm(NOT_FOUND_STORE))
                return
            }
            event.player.sendMessage(mm(REQUEST_TO_CANCEL))
        }
    }

}