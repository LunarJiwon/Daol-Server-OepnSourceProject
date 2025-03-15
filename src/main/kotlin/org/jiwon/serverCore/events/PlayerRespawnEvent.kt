package org.jiwon.serverCore.events

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent
import org.jiwon.serverCore.ServerCore.Companion.defaultConfig
import org.jiwon.serverCore.ServerCore.Companion.gson
import org.jiwon.serverCore.alternative.ConfigNames
import org.jiwon.serverCore.alternative.ConfigNames.SPAWN_LOCATION
import org.jiwon.serverCore.manager.LocationManager
import org.jiwon.serverCore.utils.LocationData

class PlayerRespawnEvent:Listener {

    @EventHandler
    private fun playerRespawnEvent(event:PlayerRespawnEvent){
        if(defaultConfig.getRawData(SPAWN_LOCATION) != null){
            event.respawnLocation =
                LocationManager.getLocation(
                    gson.fromJson(defaultConfig.getJsonObject(
                        SPAWN_LOCATION), LocationData::class.java)!!)
        }
    }

}