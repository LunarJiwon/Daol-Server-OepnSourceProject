package org.jiwon.serverCore.events.land.process.manage.nameChanage

import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.TextComponent
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.jiwon.serverCore.alternative.ReplaceTexts.LAND_NAME
import org.jiwon.serverCore.alternative.ReplaceTexts.LAND_NAME_AFTER
import org.jiwon.serverCore.events.DefaultTaskEvent
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.EventManager.unregisterEvent
import org.jiwon.serverCore.manager.LandManager
import org.jiwon.serverCore.manager.PlayerManager
import org.jiwon.serverCore.messages.LandTexts.ALREADY_USING_LAND_NAME
import org.jiwon.serverCore.messages.LandTexts.CHANGED_LAND_NAME
import org.jiwon.serverCore.messages.LandTexts.CHANGE_LAND_NAME_START
import org.jiwon.serverCore.utils.Components.mm

class LandNameConfirmEvent(private val player:Player, private val landName: String) : Listener {
    private val defaultEvent = DefaultTaskEvent(player, this, false)

    init {
        registerEvent(defaultEvent)
        player.sendMessage(mm(CHANGE_LAND_NAME_START))
    }

    @EventHandler
    private fun landNameInputEvent(event: AsyncChatEvent) {
        if (event.player == player) {
            event.isCancelled = true
            val message = (event.message() as TextComponent).content()
            if(message != "취소"){
                if(LandManager.getPlayerLandList(player)?.find { land -> land.landName == message } == null){
                    LandManager.changePlayerLandName(player,landName,message)

                    player.sendMessage(mm(CHANGED_LAND_NAME
                        .replace(LAND_NAME,landName)
                        .replace(LAND_NAME_AFTER,message)))

                    unregisterEvent(defaultEvent)
                    unregisterEvent(this)

                }else {
                    player.sendMessage(mm(ALREADY_USING_LAND_NAME))
                    PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                }
            }
        }
    }
}