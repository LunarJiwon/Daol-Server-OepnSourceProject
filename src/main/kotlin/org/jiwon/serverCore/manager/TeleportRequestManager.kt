package org.jiwon.serverCore.manager

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.jiwon.serverCore.ServerCore.Companion.instance

object TeleportRequestManager {
    private val request = HashMap<Player,ArrayList<Player>>()

    fun requestPlayer(requestPlayer:Player, player: Player):Boolean{
        if(request[player] != null) if(request[player]!!.any { p -> p == requestPlayer }) return false
        if(request[player] == null) request[player] = ArrayList<Player>()
        request[player]!!.add(requestPlayer)
        try{
            Bukkit.getScheduler().runTaskLater(instance, Runnable {
                request[player]!!.remove(requestPlayer) // 대기순열 삭제
                if(request[player]!!.isEmpty()) request.remove(player) // 메모리 해제
            },2400)
        }catch (_:Exception){

        }
        return true
    }

    fun acceptPlayer(player: Player):String?{
        if(request[player] == null) return null
        // 플레이어 없으면
        if(Bukkit.getOnlinePlayers().any{p -> request[player]!!.first() == p}){
            val acceptPlayer = request[player]!!.first().name
            request[player]!!.first().teleport(player.location)
            request[player]!!.remove(request[player]!!.first()) // 대기순열 삭제
            if(request[player]!!.isEmpty()) request.remove(player) // 메모리 해제
            return acceptPlayer
        }
        return null
    }

    fun denyPlayer(player:Player):String?{
        if(request[player] == null) return null
        val denyPlayer = request[player]!!.first().name
        request[player]!!.remove(request[player]!!.first())
        if(request[player]!!.isEmpty()) request.remove(player) // 메모리 해제
        return denyPlayer

    }

}