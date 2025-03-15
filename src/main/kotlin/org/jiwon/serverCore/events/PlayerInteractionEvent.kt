package org.jiwon.serverCore.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.jiwon.serverCore.events.economy.PlayerStoreSignClickEvent
import org.jiwon.serverCore.events.prefix.PrefixApplyEvent

class PlayerInteractionEvent : Listener{



    @EventHandler
    private fun interactEvent(event:PlayerInteractEvent){
        PlayerStoreSignClickEvent(event) // 상점
        PrefixApplyEvent(event)
    }
}