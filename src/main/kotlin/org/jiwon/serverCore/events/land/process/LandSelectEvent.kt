package org.jiwon.serverCore.events.land.process

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.data.BlockData
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.server.ServiceUnregisterEvent
import org.jiwon.serverCore.alternative.ReplaceTexts.TARGET_PLAYER_NAME
import org.jiwon.serverCore.events.DefaultTaskEvent
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.EventManager.unregisterEvent
import org.jiwon.serverCore.manager.LandManager
import org.jiwon.serverCore.manager.PlayerManager
import org.jiwon.serverCore.manager.PlayerManager.getPlayer
import org.jiwon.serverCore.manager.PlayerManager.getPlayerName
import org.jiwon.serverCore.messages.LandTexts.OVERLAP_AREA
import org.jiwon.serverCore.utils.Components.mm

class LandSelectEvent(val player: Player, val landName:String?,
                      private val secondLandName:String?, val callback: (Location)->Unit):Listener {

    private val defaultEvent = DefaultTaskEvent(player,this,true)

    init{
        registerEvent(defaultEvent)
    }


    @EventHandler
    private fun locationClickEvent(event:PlayerInteractEvent){
        if(event.player == player){
            event.isCancelled = true
            if(event.clickedBlock != null) {
                if (event.action == Action.LEFT_CLICK_BLOCK) {
                    if (event.clickedBlock!!.type != Material.AIR) {
                        val owner = LandManager.isOverlapLocation(event.clickedBlock!!.location)
                        if (owner.isNotEmpty()) {
                            if (landName != null) {
                                if (owner.filterNot { land -> land.key == player.uniqueId.toString() && land.value.landName == landName || land.value.landName == secondLandName}
                                        .isEmpty()) {

                                    player.sendBlockChange(
                                        event.clickedBlock!!.location.add(0.0, 1.0, 0.0),
                                        Bukkit.createBlockData(Material.GLASS)
                                    )
                                    callback(event.clickedBlock!!.location)
                                    unregisterEvent(defaultEvent)
                                    unregisterEvent(this)
                                    return
                                }
                            }
                            player.sendMessage(
                                mm(
                                    OVERLAP_AREA
                                )
                            )
                            PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                            return
                        }
                        player.sendBlockChange(
                            event.clickedBlock!!.location.add(0.0, 1.0, 0.0),
                            Bukkit.createBlockData(Material.GLASS)
                        )
                        callback(event.clickedBlock!!.location)
                        unregisterEvent(defaultEvent)
                        unregisterEvent(this)

                    }
                }
            }
            return
        }
    }

}