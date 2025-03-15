package org.jiwon.serverCore.events.warp

import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.jiwon.serverCore.alternative.ReplaceTexts.WARP_NAME
import org.jiwon.serverCore.manager.LocationManager
import org.jiwon.serverCore.manager.PlayerManager
import org.jiwon.serverCore.manager.WarpManager
import org.jiwon.serverCore.messages.WarpMessages.TELEPORTED_WARP
import org.jiwon.serverCore.utils.Components.mm

class PlayerWarpEvent(event:PlayerCommandPreprocessEvent, player: Player, warpName:String) {

    init{
        if(player.world.name != "tutorial"){
            val warp = WarpManager.getWarp(warpName)
            if(warp != null){
                player.teleport(
                    LocationManager.getLocation(warp.location)
                )
                PlayerManager.playerSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP)
                if(warp.isMessage)  player.sendMessage(mm(
                    TELEPORTED_WARP.replace(WARP_NAME,warp.warpName)
                ))
                event.isCancelled = true
            }
        }
    }

}