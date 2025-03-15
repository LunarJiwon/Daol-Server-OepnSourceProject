package org.jiwon.serverCore.events

import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.TextComponent
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.jiwon.serverCore.manager.EventManager.unregisterEvent
import org.jiwon.serverCore.messages.SystemMessages.CANCEL_TASK
import org.jiwon.serverCore.messages.SystemMessages.REQUEST_TO_CANCEL
import org.jiwon.serverCore.utils.Components.mm

class DefaultInventoryEvent(private val player: Player, private val listener: Listener):Listener {

    @EventHandler
    private fun quitInventory(event:InventoryCloseEvent){
        if(event.player == player){
            unregisterEvent(listener)
            unregisterEvent(this)
        }
    }

    @EventHandler
    private fun leaveEvent(event: PlayerQuitEvent){
        if(event.player == player) {
            unregisterEvent(listener)
            unregisterEvent(this)
        }
    }

}