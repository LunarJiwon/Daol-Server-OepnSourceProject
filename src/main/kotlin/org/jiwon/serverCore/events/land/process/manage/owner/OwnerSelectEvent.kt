package org.jiwon.serverCore.events.land.process.manage.owner

import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.TextComponent
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.jiwon.serverCore.ServerCore.Companion.defaultConfig
import org.jiwon.serverCore.alternative.ConfigNames.LAND_TAX
import org.jiwon.serverCore.alternative.ReplaceTexts.LAND_NAME
import org.jiwon.serverCore.alternative.ReplaceTexts.TARGET_PLAYER_NAME
import org.jiwon.serverCore.alternative.ReplaceTexts.TAX
import org.jiwon.serverCore.events.DefaultTaskEvent
import org.jiwon.serverCore.manager.AreaManager
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.EventManager.unregisterEvent
import org.jiwon.serverCore.manager.LandManager
import org.jiwon.serverCore.manager.PlayerManager
import org.jiwon.serverCore.messages.BalanceMessages.NOT_FOUND_PLAYER
import org.jiwon.serverCore.messages.LandTexts.LAND_OWNER_CHANGE_CONFIRM
import org.jiwon.serverCore.messages.LandTexts.LAND_OWNER_CHANGE_TARGET_PLAYER_MAX_LAND
import org.jiwon.serverCore.messages.LandTexts.SELF_APPEND
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.NumberUtils

class OwnerSelectEvent(private val player:Player, private val landName:String,private val price:Int):Listener {

    private val defaultTaskEvent = DefaultTaskEvent(player,this,false)

    init{
        registerEvent(defaultTaskEvent)
    }

    @EventHandler
    private fun ownerSelectEvent(event:AsyncChatEvent){
        if(event.player == player){
            event.isCancelled = true
            val message = (event.message() as TextComponent).content()
            if(message != "취소"){
                val targetPlayer = Bukkit.getServer().onlinePlayers.find { it.player?.name == message }
                if(targetPlayer != null){
                    if(targetPlayer == player){
                        player.sendMessage(mm(SELF_APPEND))
                        PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                        return
                    }
                    if(LandManager.getPlayerLandSize(player) > 7){
                        player.sendMessage(mm(LAND_OWNER_CHANGE_TARGET_PLAYER_MAX_LAND.replace(LAND_NAME,targetPlayer.name)))
                        PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                        return
                    }
                    player.sendMessage(mm(LAND_OWNER_CHANGE_CONFIRM
                        .replace(LAND_NAME,landName)
                        .replace(TARGET_PLAYER_NAME,targetPlayer.player?.name!!)
                        .replace(TAX,NumberUtils.format(
                            price
                        ))))
                    registerEvent(OwnerChangeConfirmEvent(player,targetPlayer,landName,price))
                    unregisterEvent(defaultTaskEvent)
                    unregisterEvent(this)
                }else{
                    player.sendMessage(mm(NOT_FOUND_PLAYER.replace(TARGET_PLAYER_NAME,message)))
                    PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                }
            }
        }
    }

}