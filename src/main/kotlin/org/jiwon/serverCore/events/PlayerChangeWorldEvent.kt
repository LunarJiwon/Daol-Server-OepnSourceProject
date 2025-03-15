package org.jiwon.serverCore.events

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerChangedWorldEvent
import org.jiwon.serverCore.ServerCore
import org.jiwon.serverCore.ServerCore.Companion.defaultConfig
import org.jiwon.serverCore.alternative.ConfigNames.AFK_WORLD
import org.jiwon.serverCore.manager.AFKManager
import java.util.*

class PlayerChangeWorldEvent:Listener {
    @EventHandler
    private fun worldChangeEvent(event:PlayerChangedWorldEvent){
        if(defaultConfig.getRawData(AFK_WORLD) != null){
            val afkWorld = Bukkit.getWorld(UUID.fromString(defaultConfig.getString(AFK_WORLD)))!!
            if(event.from == afkWorld){
                AFKManager.stopPlayerAFK(event.player)
            }else if(event.player.world == afkWorld){
                AFKManager.startPlayerAFK(event.player)
            }
        }

    }
}