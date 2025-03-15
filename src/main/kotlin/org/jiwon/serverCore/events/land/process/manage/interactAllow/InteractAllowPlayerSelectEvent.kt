package org.jiwon.serverCore.events.land.process.manage.interactAllow

import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.TextComponent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.jiwon.serverCore.alternative.ReplaceTexts.LAND_NAME
import org.jiwon.serverCore.alternative.ReplaceTexts.PLAYER_NAME
import org.jiwon.serverCore.alternative.ReplaceTexts.TARGET_PLAYER_NAME
import org.jiwon.serverCore.events.DefaultTaskEvent
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.EventManager.unregisterEvent
import org.jiwon.serverCore.manager.LandManager
import org.jiwon.serverCore.manager.PlayerManager.getPlayer
import org.jiwon.serverCore.messages.BalanceMessages.NOT_FOUND_PLAYER
import org.jiwon.serverCore.messages.LandTexts.APPEND_BUILD_ALLOW_PLAYER
import org.jiwon.serverCore.messages.LandTexts.APPEND_INTERACT_ALLOW_PLAYER
import org.jiwon.serverCore.messages.LandTexts.SELF_APPEND
import org.jiwon.serverCore.utils.Components.mm

class InteractAllowPlayerSelectEvent(private val player:Player, private val landName:String) :Listener {

    private val defaultTaskEvent = DefaultTaskEvent(player,this,false)

    init{
        registerEvent(defaultTaskEvent)
    }

    @EventHandler
    private fun playerSelectEvent(event:AsyncChatEvent){
        if(event.player == player){
            event.isCancelled = true
            val message = (event.message() as TextComponent).content()
            if(message != "취소"){
                val targetPlayer = Bukkit.getServer().onlinePlayers.find { it.player?.name == message }
                if(targetPlayer != null){
                    if(targetPlayer.player == player){
                        player.sendMessage(mm(SELF_APPEND))
                        return
                    }
                    LandManager.addInteractAllowPlayer(player,landName, targetPlayer.uniqueId.toString())
                    player.sendMessage(mm(
                        APPEND_INTERACT_ALLOW_PLAYER.replace(PLAYER_NAME,targetPlayer.name).replace(
                        LAND_NAME,landName)))
                    unregisterEvent(defaultTaskEvent)
                    unregisterEvent(this)
                }else{
                    player.sendMessage(mm(NOT_FOUND_PLAYER.replace(TARGET_PLAYER_NAME,message)))
                }
            }
        }
    }

}