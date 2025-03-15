package org.jiwon.serverCore.manager

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.jiwon.serverCore.ServerCore.Companion.instance
//import org.jiwon.serverCore.ServerCore.Companion.muteData
import org.jiwon.serverCore.alternative.ConfigNames.MUTE_PLAYERS
import java.util.UUID
import kotlin.time.Duration

object MuteManager {

//    private val mutePlayers = HashMap<UUID,Int>()
//
//    fun firstStart(){
//        if(muteData.getRawData(MUTE_PLAYERS) != null){
//            muteData.getJsonArray(MUTE_PLAYERS).forEach {
//                mutePlayers[UUID.fromString(it.asJsonObject.get("UUID").asString)] = Bukkit.getScheduler().scheduleSyncDelayedTask(instance,
//                    Runnable {
//
//                    },(it.asJsonObject.get("duration").asInt * 20).toLong())
//            }
//        }
//    }
//
//    private fun addPlayer(uuid: UUID,duration: Int){
//        mutePlayers[uuid] = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance,
//            Runnable {
//                muteData.getJsonArray(MUTE_PLAYERS).find { u -> UUID.fromString(u.asJsonObject.get("UUID").asString) == uuid }!!.asJsonObject.get("duration").asInt
//            },20,20)
//    }
//
//    fun playerMute(player: Player,targetPlayer: Player,duration:Int){
//
//    }
//
//    fun playerUnMute(player: Player,targetPlayer: Player){
//
//    }
}