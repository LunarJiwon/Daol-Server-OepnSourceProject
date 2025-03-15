package org.jiwon.serverCore.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerPortalEvent

class PortalEvent:Listener {
    @EventHandler
    private fun portalEvent(event:PlayerPortalEvent){
        event.isCancelled = true
    }
}