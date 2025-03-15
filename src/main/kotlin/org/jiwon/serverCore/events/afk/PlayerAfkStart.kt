package org.jiwon.serverCore.events.afk

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.jiwon.serverCore.ServerCore.Companion.defaultConfig
import org.jiwon.serverCore.alternative.ConfigNames.AFK_WORLD
import org.jiwon.serverCore.manager.AFKManager
import java.util.*

object PlayerAfkStart {

    fun playerCheck(player:Player){
        if(defaultConfig.getRawData(AFK_WORLD) != null) {
            val afkWorld = Bukkit.getWorld(UUID.fromString(defaultConfig.getString(AFK_WORLD)))!!
            if (player.world == afkWorld){
                AFKManager.startPlayerAFK(player)
            }
        }
    }

}