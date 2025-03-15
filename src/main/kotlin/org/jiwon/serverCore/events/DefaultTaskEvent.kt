package org.jiwon.serverCore.events

import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.TextComponent
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.jiwon.serverCore.events.economy.PlayerStoreSignClickEvent
import org.jiwon.serverCore.manager.EventManager.unregisterEvent
import org.jiwon.serverCore.messages.SystemMessages.CANCEL_TASK
import org.jiwon.serverCore.messages.SystemMessages.REQUEST_TO_CANCEL
import org.jiwon.serverCore.utils.Components.mm

class DefaultTaskEvent(private val player:Player, private val listener: Listener, private val isCancelMessage:Boolean):Listener {



    @EventHandler(priority = EventPriority.HIGH)
    private fun preCommand(event: PlayerCommandPreprocessEvent){
        if(event.player == player){
            event.isCancelled = true
            event.player.sendMessage(mm(REQUEST_TO_CANCEL))

        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private fun cancelChat(event: AsyncChatEvent){
        if(event.player == player) {
            event.isCancelled = true
            val message = (event.message() as TextComponent).content()
            if (message == "취소") {
                event.player.sendMessage(mm(CANCEL_TASK))
                PlayerStoreSignClickEvent.ignorePlayer.remove(player)
                unregisterEvent(listener)
                unregisterEvent(this)
            } else if(message != "확인" && isCancelMessage) event.player.sendMessage(mm(REQUEST_TO_CANCEL))
        }
    }

    @EventHandler
    private fun leaveEvent(event: PlayerQuitEvent){
        if(event.player == player) {
            PlayerStoreSignClickEvent.ignorePlayer.remove(player)
            unregisterEvent(listener)
            unregisterEvent(this)
        }
    }
}