package org.jiwon.serverCore.events.land.process.manage.settings

import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.TextComponent
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.jiwon.serverCore.events.DefaultTaskEvent
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.EventManager.unregisterEvent
import java.util.function.Consumer

class LandSettingPlayerInputEvent(private val player:Player, private val callback:(String)->Unit):Listener {
    private val defaultTaskEvent = DefaultTaskEvent(player,this,false)

    init {
        registerEvent(defaultTaskEvent)
    }

    @EventHandler
    private fun playerInputEvent(event:AsyncChatEvent){
        if(event.player == player){
            event.isCancelled = true
            val message = (event.message() as TextComponent).content()
            if(message != "취소"){
                callback(message)
                unregisterEvent(defaultTaskEvent)
                unregisterEvent(this)
            }
        }
    }
}