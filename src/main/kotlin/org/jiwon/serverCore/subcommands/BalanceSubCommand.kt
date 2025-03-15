package org.jiwon.serverCore.subcommands

import org.bukkit.entity.Player
import org.geysermc.floodgate.api.player.FloodgatePlayer
import org.jiwon.serverCore.ServerCore

abstract class BalanceSubCommand(player:Player, vararg args:String) {

    init {
        initialize(player,*args)
    }

    private fun initialize(player: Player, vararg args: String) {
        if (javaPlatformCheck(player)) {
            javaRun(player, *args)
        } else {
            bedrockRun(ServerCore.floodgateInstance.getPlayer(player.uniqueId), *args)
        }
    }

    /**
     * @description 자바 플레이어 실행 함수
     * @param player 대상 플레이어
     * @param args 인자
     */
    abstract fun javaRun(player: Player,vararg args:String)

    /**
     * @description 베드락 플레이어 실행 함수
     * @param player 대상 플레이어
     * @param args 인자
     */
    abstract fun bedrockRun(player: FloodgatePlayer,vararg args:String)

    private fun javaPlatformCheck(player: Player):Boolean{
        return !ServerCore.floodgateInstance.isFloodgatePlayer(player.uniqueId)
    }
}