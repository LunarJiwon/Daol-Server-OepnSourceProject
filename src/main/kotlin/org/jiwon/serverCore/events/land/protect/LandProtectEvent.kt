package org.jiwon.serverCore.events.land.protect

import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockExplodeEvent
import org.bukkit.event.block.BlockFromToEvent
import org.bukkit.event.block.BlockPistonExtendEvent
import org.bukkit.event.block.BlockPistonRetractEvent
import org.bukkit.event.entity.EntityExplodeEvent
import org.bukkit.event.entity.EntityInteractEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent

class LandProtectEvent:Listener {

    @EventHandler
    private fun interactEvent(event:PlayerInteractEvent){
        if(!event.player.hasPermission("server.manage")) {
            if (event.clickedBlock != null) {
                event.isCancelled = LandProtectProcess.protectBuild(event.clickedBlock!!.location, event.player)
            } else {
                event.isCancelled = LandProtectProcess.protectAll(event.player.location, event.player)
            }
        }

    }

//    @EventHandler
//    private fun entityInteractEvent(event:EntityInteractEvent){
//        event.isCancelled = LandProtectProcess.protectAll(event.entity.location,null)
//    }

    @EventHandler
    private fun interactToEntityEvent(event:PlayerInteractEntityEvent){
        if(!event.player.hasPermission("server.manage")) {
            event.isCancelled = LandProtectProcess.protectBuild(event.rightClicked.location, event.player)
        }
    }

    @EventHandler
    private fun explodeEvent(event:EntityExplodeEvent){
        event.blockList().forEach {
            event.isCancelled = LandProtectProcess.protectExplode(it.location)
        }
    }

    @EventHandler
    private fun explodeBlockEvent(event:BlockExplodeEvent){
        event.blockList().forEach {
            event.isCancelled = LandProtectProcess.protectExplode(it.location)
        }
    }

    @EventHandler
    private fun flowBlockEvent(event:BlockFromToEvent){
        event.isCancelled = LandProtectProcess.protectFlowBlock(event.block.location,event.toBlock.location)
    }

    @EventHandler
    private fun pistonEvent(event: BlockPistonExtendEvent){
        event.isCancelled = LandProtectProcess.pistonProtect(event.blocks)
    }

    @EventHandler
    private fun pistonPullEvent(event:BlockPistonRetractEvent){
        event.isCancelled = LandProtectProcess.pistonProtect(event.blocks)
    }




}