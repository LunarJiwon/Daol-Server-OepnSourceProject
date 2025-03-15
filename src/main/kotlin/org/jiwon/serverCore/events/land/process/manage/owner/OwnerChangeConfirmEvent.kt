package org.jiwon.serverCore.events.land.process.manage.owner

import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.TextComponent
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.jiwon.serverCore.alternative.ReplaceTexts.LAND_NAME
import org.jiwon.serverCore.alternative.ReplaceTexts.TARGET_PLAYER_NAME
import org.jiwon.serverCore.events.DefaultTaskEvent
import org.jiwon.serverCore.manager.BalanceManager
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.EventManager.unregisterEvent
import org.jiwon.serverCore.manager.LandManager
import org.jiwon.serverCore.manager.PlayerManager
import org.jiwon.serverCore.messages.BalanceMessages.NOT_FOUND_PLAYER
import org.jiwon.serverCore.messages.LandTexts.LAND_OWNER_CHANGED
import org.jiwon.serverCore.messages.LandTexts.LAND_OWNER_CHANGE_TARGET_ALREADY_NAME
import org.jiwon.serverCore.utils.Components.mm

class OwnerChangeConfirmEvent(private val player:Player, private val targetPlayer: Player, private val landName:String,private val price:Int):Listener {

    private val defaultTaskEvent = DefaultTaskEvent(player,this,true)

    init{
        registerEvent(defaultTaskEvent)
    }

    @EventHandler
    private fun ownerChangeConfirmEvent(event:AsyncChatEvent){
        if(event.player == player){
            event.isCancelled = true
            val message = (event.message() as TextComponent).content()
            if(message == "확인"){
                if(targetPlayer.isOnline){
                    if(LandManager.getPlayerLand(targetPlayer,landName) == null){
                        LandManager.createPlayerLand(targetPlayer,LandManager.getPlayerLand(player, landName)!!)
                        LandManager.removePlayerLandFromLandName(player,landName)
                        BalanceManager.removePlayerBalance(player,price)
                        player.sendMessage(mm(LAND_OWNER_CHANGED.replace(TARGET_PLAYER_NAME,targetPlayer.name).replace(
                            LAND_NAME,landName)))
                        unregisterEvent(defaultTaskEvent)
                        unregisterEvent(this)
                    }else{
                        player.sendMessage(mm(LAND_OWNER_CHANGE_TARGET_ALREADY_NAME))
                        PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                    }
                }else{
                    PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                    player.sendMessage(mm(NOT_FOUND_PLAYER.replace(TARGET_PLAYER_NAME,targetPlayer.name)))
                    unregisterEvent(defaultTaskEvent)
                    unregisterEvent(this)
                }
            }
        }
    }

}