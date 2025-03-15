package org.jiwon.serverCore.manager

import org.bukkit.Bukkit
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.jiwon.serverCore.ServerCore.Companion.instance

object EventManager {
    fun registerEvent(listener: Listener){
        Bukkit.getPluginManager().registerEvents(listener,instance)
    }

    fun unregisterEvent(listener: Listener){
        HandlerList.unregisterAll(listener)
    }
}